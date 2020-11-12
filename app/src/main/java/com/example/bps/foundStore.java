package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bps.adapter.StoreItemRecyclerAdapter;
import com.example.bps.model.StoreItemDetail;

import java.util.ArrayList;
import java.util.List;

public class foundStore extends AppCompatActivity {

    private RecyclerView foundStoreItemRecycler;
    private StoreItemRecyclerAdapter adapter;
    private ImageView storeIcon, storePromotion;
    private TextView storeName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_store);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("StoreName");
        int intIconUrl = intent.getExtras().getInt("StoreIconUrl");

        storeIcon = findViewById(R.id.foundStore_profile);
        storeIcon.setImageResource(intIconUrl);
        storeName = findViewById(R.id.foundStore_text_title);
        storeName.setText(stringName);
        storePromotion = findViewById(R.id.foundStore_promotion);
        storePromotion.setImageResource(R.drawable.on_sale);

        List<StoreItemDetail> storeItemDetails = new ArrayList<>();
        storeItemDetails.add(new StoreItemDetail("Item1",10.00,1,R.drawable.item_clipart1));
        storeItemDetails.add(new StoreItemDetail("Item2",12.50,1,R.drawable.item_clipart2));
        storeItemDetails.add(new StoreItemDetail("Item3",20.00,1,R.drawable.item_clipart3));
        storeItemDetails.add(new StoreItemDetail("Item4",15.00,1,R.drawable.item_clipart4));
        storeItemDetails.add(new StoreItemDetail("Item5",13.25,1,R.drawable.item_clipart5));
        storeItemDetails.add(new StoreItemDetail("Item6",30.00,1,R.drawable.item_clipart1));
        storeItemDetails.add(new StoreItemDetail("Item7",40.00,1,R.drawable.item_clipart2));
        storeItemDetails.add(new StoreItemDetail("Item8",100.00,1,R.drawable.item_clipart3));
        storeItemDetails.add(new StoreItemDetail("Item9",35.00,1,R.drawable.item_clipart4));
        storeItemDetails.add(new StoreItemDetail("Item10",45.00,1,R.drawable.item_clipart5));

        setItemRecycler(storeItemDetails);

        toolbar = findViewById(R.id.foundStore_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setItemRecycler(List<StoreItemDetail> storeItemDetails) {
        foundStoreItemRecycler = findViewById(R.id.foundStore_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        foundStoreItemRecycler.setLayoutManager(layoutManager);
        adapter = new StoreItemRecyclerAdapter(this,storeItemDetails);
        foundStoreItemRecycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_found_shop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.found_shop_toMain:
                intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.found_shop_setting:
                intent = new Intent(this,myStoreProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.found_shop_aboutapp:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}