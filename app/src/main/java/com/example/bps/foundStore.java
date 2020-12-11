package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.bps.model.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.spongycastle.util.Store;

import java.util.ArrayList;
import java.util.List;

public class foundStore extends AppCompatActivity {

    private RecyclerView foundStoreItemRecycler;
    private StoreItemRecyclerAdapter adapter;
    private ImageView storeIcon;
    private TextView storeName, storeDetail;
    private Toolbar toolbar;

    private DocumentReference documentReference;
    private CollectionReference goodsCollectionReference;
    private List<StoreItemDetail> storeItemDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_store);

        Intent intent = getIntent();
        String stringName = intent.getExtras().getString("StoreName");
        String storeIconUrl = intent.getExtras().getString("StoreIconUrl");
        String storeId = intent.getExtras().getString("storeID");

        storeIcon = findViewById(R.id.foundStore_profile);
        storeName = findViewById(R.id.foundStore_text_title);
        storeDetail = findViewById(R.id.foundStore_text_detail);
        storeName.setText(stringName);

        toolbar = findViewById(R.id.foundStore_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        documentReference = FirebaseFirestore.getInstance()
                .collection("users")
                .document(storeId);

        goodsCollectionReference = documentReference.collection("goods");

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                        if (userProfile.getIconUrl() == null){
                            storeIcon.setImageResource(R.drawable.user_image);
                        }else{
                            Picasso.with(foundStore.this)
                                    .load(userProfile.getIconUrl())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .fit()
                                    .centerCrop()
                                    .into(storeIcon);
                        }
                        if (userProfile.getDetail() == null){
                            storeDetail.setText("");
                        }else {
                            storeDetail.setText(userProfile.getDetail());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("error",e.getMessage());
                    }
                });

        storeItemDetailList = new ArrayList<>();
        goodsCollectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            StoreItemDetail storeItemDetail = documentSnapshot.toObject(StoreItemDetail.class);
                            storeItemDetail.setStoreId(storeId);
                            storeItemDetailList.add(storeItemDetail);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("error",e.getMessage());
                    }
                });
        setItemRecycler(storeItemDetailList);
    }

    private void setItemRecycler(List<StoreItemDetail> storeItemDetails) {
        foundStoreItemRecycler = findViewById(R.id.foundStore_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
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
                intent = new Intent(this,About_this_app.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}