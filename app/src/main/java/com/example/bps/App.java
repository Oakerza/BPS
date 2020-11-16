package com.example.bps;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.estimote.coresdk.service.BeaconManager;
import com.example.bps.model.BeaconInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static final String CHANNEL_1_ID = "channel1";

    private CollectionReference collectionReference;
    private SimpleDateFormat format;

    private BeaconManager beaconManager;

    public static List<BeaconInfo> beaconInfoList;
    public static List<String> foundStoreId;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanel();
        collectionReference = FirebaseFirestore.getInstance()
                .collection("users");
        format = new SimpleDateFormat("HH:mm");
        beaconManager = new BeaconManager(getApplicationContext());

        beaconInfoList = new ArrayList<>();
        foundStoreId = new ArrayList<>();

    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "การค้นหาร้านค้า",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
