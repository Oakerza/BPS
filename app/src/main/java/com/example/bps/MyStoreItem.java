package com.example.bps;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bps.model.StoreItemDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyStoreItem extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Double price;
    private ImageView itemImage;
    private EditText itemName, itemPrice, itemDetail;
    private Toolbar toolbar;
    private Button buttonImage, buttonUpdate, buttonDelete;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private Uri imageUri;
    private String itemImageUrl;
    private StorageTask uploadTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_item);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("ItemName");
        String stringId = intent.getExtras().getString("ItemId");
        price = intent.getExtras().getDouble("ItemPrice");
        itemImageUrl = intent.getExtras().getString("ItemImageUrl");
        int lastItem = intent.getExtras().getInt("LastItem");

        buttonUpdate = findViewById(R.id.myStoreItem_button_update);
        buttonDelete = findViewById(R.id.myStoreItem_button_delete);
        buttonImage = findViewById(R.id.myStoreItem_button_image);
        itemImage = findViewById(R.id.myStoreItem_image);
        itemName = findViewById(R.id.myStoreItem_editText_name);
        itemPrice = findViewById(R.id.myStoreItem_editText_price);
        itemDetail = findViewById(R.id.myStoreItem_editText_detail);
        progressBar = findViewById(R.id.myStoreItem_progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        if (firebaseAuth.getUid() != null) {
            if (stringId != null){
                documentReference = FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(firebaseAuth.getUid())
                        .collection("goods")
                        .document(stringId);
            }
            collectionReference = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(firebaseAuth.getUid())
                    .collection("goods");
        }


        if (lastItem == 0) {
            buttonUpdate.setText("เพิ่มสินค้า");
            buttonDelete.setText("ยกเลิก");
            buttonImage.setText("เพิ่มรูปภาพ");
            itemName.setHint("ใส่ชื่อสินค้า");
            itemPrice.setText("0");
            itemDetail.setHint("รายละเอียด");
            itemImage.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        } else {
            itemName.setText(stringName);
            itemPrice.setText(String.valueOf(price));
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    StoreItemDetail currentStoreItemDetail = documentSnapshot.toObject(StoreItemDetail.class);
                    if (currentStoreItemDetail != null) {
                        itemDetail.setText(currentStoreItemDetail.getItemInfo());
                        Picasso.with(MyStoreItem.this)
                                .load(itemImageUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .fit()
                                .centerCrop()
                                .into(itemImage);
                    } else {
                        itemDetail.setText("");
                    }
                }
            });
        }

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(MyStoreItem.this, "กำลังบันทึก", Toast.LENGTH_SHORT).show();
                }else{
                    if (lastItem == 0) {
                        Log.d("update","create Button");
                        createItem();
                    } else {
                        Log.d("update","update Button");
                        updateItem();
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastItem == 0){
                    Log.d("update","finish Button");
                    finish();
                }else {
                    Log.d("update","delete Button");
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot != null) {
                                StoreItemDetail currentStoreItemDetail = documentSnapshot
                                        .toObject(StoreItemDetail.class);
                                StorageReference storageReference = firebaseStorage
                                        .getReferenceFromUrl(currentStoreItemDetail.getImageUrl());
                                storageReference.delete();
                                documentReference.delete();
                            }
                        }
                    });
                }

            }
        });

        toolbar = findViewById(R.id.myStoreItem_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    private void updateItem() {
        if (imageUri != null) {
            Log.d("update","have uri");
            firebaseStorage.getReferenceFromUrl(itemImageUrl).delete();
            StorageReference storageReference = firebaseStorage.getReference().child("users/images/goods/" +
                    System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StoreItemDetail currentStoreItemDetail = new StoreItemDetail();
                                    currentStoreItemDetail.setItemName(itemName.getText().toString());
                                    currentStoreItemDetail.setItemPrice(Integer.parseInt(itemPrice.getText().toString()));
                                    currentStoreItemDetail.setItemInfo(itemDetail.getText().toString());
                                    currentStoreItemDetail.setImageUrl(uri.toString());
                                    documentReference.set(currentStoreItemDetail)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(MyStoreItem.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });
        }
    }

    private void createItem() {
        Log.d("update","currentStoreItemDetail");
        if (imageUri != null) {
            Log.d("update","have uri");
            StorageReference storageReference = firebaseStorage.getReference().child("users/images/goods/" +
                    System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StoreItemDetail currentStoreItemDetail = new StoreItemDetail();
                                    currentStoreItemDetail.setItemName(itemName.getText().toString());
                                    int value;
                                    if (itemPrice.getText().toString() != null){
                                        value = Integer.parseInt(itemPrice.getText().toString());
                                    }else{
                                        value = 0;
                                    }
                                    currentStoreItemDetail.setItemPrice(value);
                                    currentStoreItemDetail.setItemInfo(itemDetail.getText().toString());
                                    currentStoreItemDetail.setImageUrl(uri.toString());
                                    collectionReference.add(currentStoreItemDetail)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(MyStoreItem.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(imageUri));
    }

    private void openFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(itemImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_myshop_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.myshop_item_toMain:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.myshop_item_profile:
                intent = new Intent(this, myStoreProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.myshop_item_edit_shop:
                return false;
            case R.id.myshop_item_aboutapp:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}