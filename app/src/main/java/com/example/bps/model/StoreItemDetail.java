package com.example.bps.model;

public class StoreItemDetail {

    String itemName;
    int itemPrice;
    String itemInfo;
    String itemId;
    String imageUrl;

    public StoreItemDetail(){
    }

    public StoreItemDetail(String itemName, int itemPrice, String itemInfo, String imageUrl) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemInfo = itemInfo;
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }
}
