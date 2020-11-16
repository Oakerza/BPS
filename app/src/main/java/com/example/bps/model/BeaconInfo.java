package com.example.bps.model;

import android.text.format.Time;

public class BeaconInfo {
    private String uuid, major, miner;
    private Time time;

    public BeaconInfo(String uuid, String major, String miner, Time time) {
        this.uuid = uuid;
        this.major = major;
        this.miner = miner;
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
