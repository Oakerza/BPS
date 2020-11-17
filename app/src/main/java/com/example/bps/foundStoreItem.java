package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class foundStoreItem extends AppCompatActivity {

    private ImageView itemImage;
    private TextView itemName, itemPrice, cost;
    private EditText editTextPics;
    private static int pics = 1;
    private static int price, totalCost;
    private Button picsUp, picsDown;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_store_item);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("ItemName");
        price = intent.getExtras().getInt("ItemPrice");
        String imageUrl = intent.getExtras().getString("ItemImageUrl");

        itemImage = findViewById(R.id.foundStoreItem_image);
        if(imageUrl == null){
            itemImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.with(foundStoreItem.this)
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(itemImage);
        }

        itemName = findViewById(R.id.foundStoreItem_text_name);
        itemName.setText(stringName);
        itemPrice = findViewById(R.id.foundStoreItem_text_price);
        itemPrice.setText(String.valueOf(price));

        toolbar = findViewById(R.id.foundStoreItem_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextPics = findViewById(R.id.foundStoreItem_editText_pics);
        editTextPics.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextPics.length() != 0) {
                    pics = Integer.parseInt(editTextPics.getText().toString());
                    updateCost();
                } else {
                    pics = 1;
                    updateCost();
                }
            }
        });

        pics = Integer.parseInt(editTextPics.getText().toString());
        cost = findViewById(R.id.foundStoreItem_text_cost);
        updateCost();
        picsUp = findViewById(R.id.foundStoreItem_button_pics_add);
        picsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pics = pics + 1;
                editTextPics.setText(String.valueOf(pics));
                updateCost();
            }
        });
        picsDown = findViewById(R.id.foundStoreItem_button_pics_down);
        picsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pics > 1) {
                    pics = pics - 1;
                } else {
                    pics = 1;
                }
                editTextPics.setText(String.valueOf(pics));
                updateCost();
            }
        });
    }

    private void updateCost() {
        totalCost = pics * price;
        String textCost = String.valueOf(totalCost);
        cost.setText(textCost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_found_shop_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.found_shop_item_toMain:
                intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.found_shop_item_setting:
                intent = new Intent(this,myStoreProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.found_shop_item_aboutapp:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}