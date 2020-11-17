package com.example.bps;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.bps.model.BeaconInfo;
import com.example.bps.model.FoundStoreDetail;
import com.example.bps.model.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static final String CHANNEL_1_ID = "channel1";

    private CollectionReference collectionReference;
    private SimpleDateFormat format;

    private BeaconManager beaconManager;

    public static List<BeaconInfo> beaconInfoList;
    public static List<String> beaconList;
    public static List<FoundStoreDetail> foundStoreDetailList;

    @Override
    public void onCreate() {
        super.onCreate();
        //createNotificationChanel();
        collectionReference = FirebaseFirestore.getInstance()
                .collection("users");
        format = new SimpleDateFormat("hh:mm:ss aa");
        beaconManager = new BeaconManager(getApplicationContext());

        beaconList = new ArrayList<>();
        beaconInfoList = new ArrayList<>();
        foundStoreDetailList = new ArrayList<>();

        Log.d("beacon list", "on App");
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
//                Date date = Calendar.getInstance().getTime();
//                String stringDate = format.format(date);
//                Date date1 = null;
//                try {
//                    date1 = format.parse(stringDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                for (Beacon beacon : beacons) {
                    Log.d("beacon", beacon.getProximityUUID().toString());
                    BeaconInfo beaconInfo = new BeaconInfo(
                            beacon.getProximityUUID(),
                            beacon.getMajor(),
                            beacon.getMinor());
                    String sBeacon = beaconInfo.getUuid() +
                            String.valueOf(beaconInfo.getMajor()) +
                            beaconInfo.getMinor();
                    Log.d("beacon string", sBeacon);
                    if (!beaconList.contains(sBeacon)) {
                        beaconList.add(0, sBeacon);
                        beaconInfoList.add(0, beaconInfo);
                        Log.d("beacon list", beaconInfoList.toString());
                        findStore(beaconInfo);
                    }
                }
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("beacon list", "scan");
                beaconManager.startRanging(new BeaconRegion(
                        "", null,
                        null, null));

            }
        });

    }

    private void findStore(BeaconInfo beaconInfo) {
        collectionReference.whereArrayContains("uuid", beaconInfo.getUuid().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d("beacon in fire base", documentSnapshot.getId());
                            UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                            int index = userProfile.getUuid().indexOf(beaconInfo.getUuid().toString());
                            Log.d("check", "index : " + index +
                                    "\nfireUUID : " + userProfile.getUuid().get(index) +
                                    "\nthisUUID : " + beaconInfo.getUuid().toString() +
                                    "\nfireMajor : " + userProfile.getMajor().get(index) +
                                    "\nthisMajor : " + beaconInfo.getMajor() +
                                    "\nfireMinor : " + userProfile.getMinor().get(index) +
                                    "\nthisMinor : " + beaconInfo.getMinor());
                            if (userProfile.getMajor().get(index) == beaconInfo.getMajor()) {
                                Log.d("check","Major");
                                if (userProfile.getMinor().get(index) == beaconInfo.getMinor()) {
                                    Log.d("check","Major");
                                    FoundStoreDetail foundStoreDetail = new FoundStoreDetail(
                                            userProfile.getUserName(),
                                            documentSnapshot.getId(),
                                            userProfile.getIconUrl());
                                    foundStoreDetailList.add(0, foundStoreDetail);
                                    Log.d("Store list", foundStoreDetailList.toString());
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("findError", "\nerror" + e);
                    }
                });
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
