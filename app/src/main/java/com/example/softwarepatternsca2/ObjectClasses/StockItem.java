package com.example.softwarepatternsca2.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

public class StockItem {

    String title;
    String manufacturer;
    int price;
    String category;
    String imageUrl;
    String itemId;
    int stockAmount;
    ArrayList<String> feedback=new ArrayList<String>();
    int customerRating;
    int numOfRatings;

    public StockItem(){

    }

    public StockItem(String title, String manufacturer, int price, String category, String imageUrl, String itemId, int stockAmount) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
        this.stockAmount = stockAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public ArrayList<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(ArrayList<String> feedback) {
        this.feedback = feedback;
    }

    public int getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(int customerRating) {
        this.customerRating = customerRating;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }
}
