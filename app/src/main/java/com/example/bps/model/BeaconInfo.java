package com.example.bps.model;

import java.util.UUID;

public class BeaconInfo {
    private UUID uuid;
    private int major, minor;

    public BeaconInfo(UUID uuid, int major, int minor) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
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

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

}
