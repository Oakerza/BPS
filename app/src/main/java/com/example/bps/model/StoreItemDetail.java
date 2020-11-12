package com.example.bps.model;

public class StoreItemDetail {

    String itemName;
    Double itemPrice;
    Integer itemId;
    Integer imageUrl;

    public StoreItemDetail(String itemName, Double itemPrice, Integer itemId, Integer imageUrl) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
