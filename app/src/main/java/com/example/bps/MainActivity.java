package com.example.bps;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private int first = 0, second = 0;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress_bar_refresh);
        swipeRefreshLayout = findViewById(R.id.main_swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("refresh",App.foundStoreDetailList.toString());
                foundStoreRecyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        collectionReference = FirebaseFirestore.getInstance().collection("users");

    }

    @Override
    protected void onStart() {
        super.onStart();
        setFoundStoreRecycler(App.foundStoreDetailList);
        foundStoreRecyclerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        content();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.VISIBLE);
        content();
    }

    private void content() {
        second = first;
        first = App.foundStoreDetailList.size();

        if (first == 0){
            progressBar.setVisibility(View.VISIBLE);
        }else if (second != first){
            progressBar.setVisibility(View.INVISIBLE);
            foundStoreRecyclerAdapter.notifyDataSetChanged();
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
        refresh(5000);
    }

    private void refresh(int milliseconds) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        handler.postDelayed(runnable, milliseconds);
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
            case R.id.main_login_toolbar_reFresh:
                Log.d("refresh","refresh");
                restartMainActivity();
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
                intent = new Intent(this,About_this_app.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void restartMainActivity() {
        App.beaconInfoList.clear();
        App.beaconList.clear();
        App.foundStoreDetailList.clear();
        foundStoreRecyclerAdapter.notifyDataSetChanged();
    }
}