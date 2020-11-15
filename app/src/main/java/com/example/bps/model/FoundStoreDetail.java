package com.example.bps.model;

public class FoundStoreDetail {

    String storeName;
    String itemId;
    Integer imageUrl;

    public FoundStoreDetail(String storeName, String itemId, Integer imageUrl) {
        this.storeName = storeName;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
