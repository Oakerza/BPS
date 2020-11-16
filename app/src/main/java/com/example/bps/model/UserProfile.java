package com.example.bps.model;

import java.util.List;

public class UserProfile {
    private String userName;
    private String email;
    private String id;
    private String iconUrl;
    private List<String> uuid;
    private List<String> major;
    private List<String> miner;
    private String detail;
    private String phone;
    private String address;

    public UserProfile(){

    }

    public UserProfile(String userName, String email){
        this.userName = userName;
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<String> getUuid() {
        return uuid;
    }

    public void setUuid(List<String> uuid) {
        this.uuid = uuid;
    }

    public List<String> getMajor() {
        return major;
    }

    public void setMajor(List<String> major) {
        this.major = major;
    }

    public List<String> getMiner() {
        return miner;
    }

    public void setMiner(List<String> miner) {
        this.miner = miner;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
