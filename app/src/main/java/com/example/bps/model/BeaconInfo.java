package com.example.bps.model;

import java.util.UUID;

public class BeaconInfo {
    private UUID uuid;
    private int major, miner;

    public BeaconInfo(UUID uuid, int major, int miner) {
        this.uuid = uuid;
        this.major = major;
        this.miner = miner;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMiner() {
        return miner;
    }

    public void setMiner(int miner) {
        this.miner = miner;
    }

}
