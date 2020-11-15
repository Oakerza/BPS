package com.example.bps.model;

import com.estimote.coresdk.recognition.packets.Beacon;

import java.util.List;

public class UserProfile {
    private String userName;
    private String email;
    private List<Beacon> beacons;
    private String detail;
    private String phone;
    private String address;

    public UserProfile(){

    }

    public UserProfile(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public UserProfile(String userName, String email, List<Beacon> uuid){
        this.userName = userName;
        this.email = email;
        this.beacons = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
