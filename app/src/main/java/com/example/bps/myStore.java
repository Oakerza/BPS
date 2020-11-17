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

import com.example.bps.adapter.MyStoreItemRecyclerAdapter;
import com.example.bps.model.StoreItemDetail;
import com.example.bps.model.UserProfile;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class myStore extends AppCompatActivity {

    private RecyclerView myStoreItemRecycler;
    private MyStoreItemRecyclerAdapter adapter;
    private TextView textViewUserName, textViewEmail;
    private ImageView userImage;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String stringUserID;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);

        toolbar = findViewById(R.id.myStore_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        textViewUserName = findViewById(R.id.myStore_text_userName);
        textViewEmail = findViewById(R.id.myStore_text_detail);
        userImage = findViewById(R.id.myStore_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        stringUserID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference userDocumentReference = firebaseFirestore
                .collection("users").document(stringUserID);
        CollectionReference goodsCollectionReference = userDocumentReference.collection("goods");

        goodsCollectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<StoreItemDetail> storeItemDetails1 = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            StoreItemDetail currentStoreItemDetail = documentSnapshot.toObject(StoreItemDetail.class);
                            currentStoreItemDetail.setItemId(documentSnapshot.getId());
                            storeItemDetails1.add(currentStoreItemDetail);
                        }
                        StoreItemDetail currentStoreItemDetail = new StoreItemDetail("เพิ่มสินค้า", 0, "ข้อมูล",
                                String.valueOf(R.drawable.ic_baseline_add_circle_outline_24));
                        storeItemDetails1.add(currentStoreItemDetail);
                        setItemRecycler(storeItemDetails1);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                List<StoreItemDetail> storeItemDetails1 = new ArrayList<>();
                StoreItemDetail currentStoreItemDetail = new StoreItemDetail("เพิ่มสินค้า", 0, "ข้อมูล",
                        String.valueOf(R.drawable.ic_baseline_add_circle_outline_24));
                storeItemDetails1.add(currentStoreItemDetail);
                setItemRecycler(storeItemDetails1);
            }
        });

        userDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                if (userProfile.getUserName() == null) {
                    userProfile.setUserName("-");
                }
                if (userProfile.getEmail() == null) {
                    userProfile.setEmail("-");

                }
                if (userProfile.getIconUrl() == null){
                    userImage.setImageResource(R.drawable.user_image);
                }else {
                    Picasso.with(myStore.this)
                            .load(userProfile.getIconUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(userImage);
                }
                textViewUserName.setText(userProfile.getUserName());
                textViewEmail.setText(userProfile.getEmail());
            }
        });
    }

    private void setItemRecycler(List<StoreItemDetail> storeItemDetails) {
        myStoreItemRecycler = findViewById(R.id.myStore_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        myStoreItemRecycler.setLayoutManager(layoutManager);
        adapter = new MyStoreItemRecyclerAdapter(this, storeItemDetails);
        myStoreItemRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_myshop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.myShop_toolbar_toMain:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.myShop_toolbar_profile:
                intent = new Intent(this, myStoreProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.myShop_toolbar_aboutApp:
                return false;
            case R.id.myShop_toolbar_logout:
                SignOut();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SignOut() {
        firebaseAuth.signOut();
        facebookSignOut();
        //LoginManager.getInstance().logOut();
        GoogleSignIn.getClient(this, new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        });
    }

    private void facebookSignOut() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        } else {
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                    .Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    LoginManager.getInstance().logOut();
                }
            }).executeAsync();
        }
    }
}