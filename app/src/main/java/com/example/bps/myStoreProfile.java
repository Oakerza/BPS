package com.example.bps;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bps.model.UserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class myStoreProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView storeIcon;
    private EditText storeName;
    private RecyclerView recyclerView;
    private Button buttonUpdate;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private EditText storeEmail, storeInfo, storePhone, storeAddress,
            beaconUuid, beaconMajor, beaconMinor;
    private Uri imageUri;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private String oldIconUrl;
    private DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_profile);

        storeIcon = findViewById(R.id.myStoreProfile_icon);
        storeName = findViewById(R.id.myStoreProfile_name);
        storeEmail = findViewById(R.id.myStoreProfile_email);
        storeInfo = findViewById(R.id.myStoreProfile_info);
        storePhone = findViewById(R.id.myStoreProfile_phone);
        storeAddress = findViewById(R.id.myStoreProfile_address);
        beaconUuid = findViewById(R.id.myStoreProfile_uuid);
        beaconMajor = findViewById(R.id.myStoreProfile_major);
        beaconMinor = findViewById(R.id.myStoreProfile_miner);
        progressBar = findViewById(R.id.myStoreProfile_progressBar);
        buttonUpdate = findViewById(R.id.myStoreProfile_button_update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        documentReference = firebaseFirestore
                .collection("users")
                .document(firebaseAuth.getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                if (userProfile.getUserName() != null) {
                    storeName.setText(userProfile.getUserName());
                } else {
                    storeName.setText("");
                }
                if (userProfile.getIconUrl() != null) {
                    oldIconUrl = userProfile.getIconUrl();
                    Picasso.with(myStoreProfile.this)
                            .load(oldIconUrl)
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(storeIcon);
                } else {
                    storeIcon.setImageResource(R.drawable.user_image);
                }
                if (userProfile.getEmail() != null) {
                    storeEmail.setText(userProfile.getEmail());
                } else {
                    storeEmail.setText("");
                }
                if (userProfile.getDetail() != null) {
                    storeInfo.setText(userProfile.getDetail());
                } else {
                    storeInfo.setText("");
                }
                if (userProfile.getPhone() != null) {
                    storePhone.setText(userProfile.getPhone());
                } else {
                    storePhone.setText("");
                }
                if (userProfile.getAddress() != null) {
                    storeAddress.setText(userProfile.getAddress());
                } else {
                    storeAddress.setText("");
                }
                if (userProfile.getUuid() != null) {
                    beaconUuid.setText(userProfile.getUuid().get(0));
                } else {
                    beaconUuid.setText("");
                }
                if (userProfile.getMajor() != null) {
                    beaconMajor.setText(String.valueOf(userProfile.getMajor().get(0)));
                } else {
                    beaconMajor.setText("");
                }
                if (userProfile.getMinor() != null) {
                    beaconMinor.setText(String.valueOf(userProfile.getMinor().get(0)));
                } else {
                    beaconMinor.setText("");
                }
            }
        });

        storeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(myStoreProfile.this, "กำลังบันทึก", Toast.LENGTH_SHORT).show();
                } else {
                    updateItem();
                }

            }
        });

    }

    private void updateItem() {
        if (imageUri != null) {
            if (oldIconUrl != null) {
                firebaseStorage.getReferenceFromUrl(oldIconUrl).delete();
            }
            StorageReference storageReference = firebaseStorage.getReference().child("users/images/icons/" +
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
                                    UserProfile currentUserProfile = new UserProfile();
                                    currentUserProfile.setUserName(storeName.getText().toString());
                                    currentUserProfile.setEmail(storeEmail.getText().toString());
                                    currentUserProfile.setDetail(storeInfo.getText().toString());
                                    currentUserProfile.setPhone(storePhone.getText().toString());
                                    currentUserProfile.setAddress(storeAddress.getText().toString());
                                    currentUserProfile.setIconUrl(uri.toString());
                                    List<String> uuids = new ArrayList<>();
                                    uuids.add(beaconUuid.getText().toString());
                                    List<Integer> majors = new ArrayList<>();
                                    majors.add(Integer.parseInt(beaconMajor.getText().toString()));
                                    List<Integer> miners = new ArrayList<>();
                                    miners.add(Integer.parseInt(beaconMinor.getText().toString()));
                                    currentUserProfile.setUuid(uuids);
                                    currentUserProfile.setMajor(majors);
                                    currentUserProfile.setMinor(miners);
                                    documentReference.set(currentUserProfile)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(myStoreProfile.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                                                    recreate();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() /
                                    snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            UserProfile currentUserProfile = new UserProfile();
            currentUserProfile.setUserName(storeName.getText().toString());
            currentUserProfile.setEmail(storeEmail.getText().toString());
            currentUserProfile.setDetail(storeInfo.getText().toString());
            currentUserProfile.setPhone(storePhone.getText().toString());
            currentUserProfile.setAddress(storeAddress.getText().toString());
            List<String> uuids = new ArrayList<>();
            uuids.add(beaconUuid.getText().toString());
            List<Integer> majors = new ArrayList<>();
            majors.add(Integer.parseInt(beaconMajor.getText().toString()));
            List<Integer> minors = new ArrayList<>();
            minors.add(Integer.parseInt(beaconMinor.getText().toString()));
            currentUserProfile.setUuid(uuids);
            currentUserProfile.setMajor(majors);
            currentUserProfile.setMinor(minors);
            documentReference.set(currentUserProfile)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(myStoreProfile.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                            recreate();
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
            Picasso.with(this).load(imageUri).into(storeIcon);
        }
    }
}