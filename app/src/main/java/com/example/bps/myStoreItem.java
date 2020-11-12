package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class myStoreItem extends AppCompatActivity {

    private Double price;
    private ImageView itemImage;
    private EditText itemName, itemPrice, itemDetail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_item);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("ItemName");
        price = intent.getExtras().getDouble("ItemPrice");
        int imageUrl = intent.getExtras().getInt("ItemImageUrl");

        itemImage = findViewById(R.id.myStoreItem_image);
        itemImage.setImageResource(imageUrl);
        itemName = findViewById(R.id.myStoreItem_editText_name);
        itemName.setText(stringName);
        itemPrice = findViewById(R.id.myStoreItem_editText_price);
        itemPrice.setText(price.toString());
        itemDetail = findViewById(R.id.myStoreItem_editText_detail);
        itemDetail.setText("---");

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