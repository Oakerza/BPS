package com.example.bps.model;

public class BeaconInfo {
    private String uuid, major, miner;

    public BeaconInfo(String uuid, String major, String miner) {
        this.uuid = uuid;
        this.major = major;
        this.miner = miner;
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
}
