package com.example.bps.model;

public class FoundStoreDetail {

    String storeName;
    String storeId;
    String imageUrl;

    public FoundStoreDetail(String storeName, String storeId, String imageUrl) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.imageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
