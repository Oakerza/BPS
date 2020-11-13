package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bps.model.StoreItemDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class myStoreItem extends AppCompatActivity {

    private Double price;
    private ImageView itemImage;
    private EditText itemName, itemPrice, itemDetail;
    private Toolbar toolbar;
    private Button buttonUpdate, buttonDelete;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_item);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("ItemName");
        price = intent.getExtras().getDouble("ItemPrice");
        int imageUrl = intent.getExtras().getInt("ItemImageUrl");
        int lastItem = intent.getExtras().getInt("LastItem");

        buttonUpdate = findViewById(R.id.myStoreItem_button_update);
        buttonDelete = findViewById(R.id.myStoreItem_button_delete);
        itemImage = findViewById(R.id.myStoreItem_image);
        itemName = findViewById(R.id.myStoreItem_editText_name);
        itemPrice = findViewById(R.id.myStoreItem_editText_price);
        itemDetail = findViewById(R.id.myStoreItem_editText_detail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        itemImage.setImageResource(imageUrl);
        if (lastItem == 0){
            buttonUpdate.setText("เพิ่มสินค้า");
            buttonDelete.setText("ยกเลิก");
            itemName.setText("");
            itemName.setHint("ใส่ชื่อสินค้า");
            itemPrice.setText("");
            itemPrice.setHint("0.00");
            itemDetail.setText("");
            itemDetail.setHint("รายละเอียด");
        }else{
            String stringId = intent.getExtras().getString("ItemId");
            itemName.setText(stringName);
            itemPrice.setText(String.valueOf(price));
            DocumentReference documentReference = firebaseFirestore
                    .collection("users")
                    .document(firebaseAuth.getUid())
                    .collection("goods")
                    .document(stringId);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    StoreItemDetail currentStoreItemDetail = documentSnapshot.toObject(StoreItemDetail.class);
                    itemDetail.setText(currentStoreItemDetail.getItemInfo());
                }
            });
        }

        toolbar = findViewById(R.id.myStoreItem_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.myshop_item_profile:
                intent = new Intent(this,myStoreProfile.class);
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