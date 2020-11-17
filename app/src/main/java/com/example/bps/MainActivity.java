package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bps.adapter.FoundStoreRecyclerAdapter;
import com.example.bps.model.FoundStoreDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView foundStoreRecycler;
    private FoundStoreRecyclerAdapter foundStoreRecyclerAdapter;
    private Button buttonMyStore;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        collectionReference = FirebaseFirestore.getInstance().collection("users");

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void showStore() {
        setFoundStoreRecycler(App.foundStoreDetailList);
    }

    private void setFoundStoreRecycler(List<FoundStoreDetail> foundStoreDetailList) {
        foundStoreRecycler = findViewById(R.id.main_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        foundStoreRecycler.setLayoutManager(layoutManager);
        foundStoreRecyclerAdapter = new FoundStoreRecyclerAdapter(this,foundStoreDetailList);
        foundStoreRecycler.setAdapter(foundStoreRecyclerAdapter);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, logIn.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            menuInflater.inflate(R.menu.menu_main_page_logined, menu);
        }else{
            menuInflater.inflate(R.menu.menu_main_page, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.main_toolbar_reFresh:
                Log.d("refresh","refresh");
                showStore();
                return true;
            case R.id.main_toolbar_category:
                return false;
            case R.id.main_toolbar_login:
                intent = new Intent(this,logIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.main_login_toolbar_myProfile:
                intent = new Intent(this,myStore.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            case R.id.main_toolbar_settings:
                return false;
            case R.id.main_toolbar_about:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}