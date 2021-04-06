package com.example.softwarepatternsca2.ObjectClasses;

import java.util.ArrayList;
import java.util.List;

public class StockItem implements Comparable<StockItem>{

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
    boolean removed;

    public StockItem(){

    }

    public StockItem(String title, String manufacturer, int price, String category, String imageUrl, String itemId, int stockAmount, boolean removed) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.itemId = itemId;
        this.stockAmount = stockAmount;
        this.removed = removed;
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public int compareTo(StockItem o) {

//        int compareInt =this.manufacturer.compareTo(o.getManufacturer());
//        if(compareInt<0)return -1;
//        if(compareInt>0)return 1;
        return 0;
    }

    public int compareManufacture(StockItem o){
        int compareInt =this.manufacturer.compareTo(o.getManufacturer());
        if(compareInt<0)return -1;
        if(compareInt>0)return 1;
        return 0;
    }

    public int compareTitle(StockItem o){
        int compareInt =this.title.compareTo(o.getTitle());
        if(compareInt<0)return -1;
        if(compareInt>0)return 1;
        return 0;
    }

    public int compareCategory(StockItem o){
        int compareInt =this.category.compareTo(o.getCategory());
        if(compareInt<0)return -1;
        if(compareInt>0)return 1;
        return 0;
    }

    public int comparePrice(StockItem o){

        int compareInt =String.valueOf(this.price).compareTo(String.valueOf(o.getPrice()));
        if(compareInt<0)return -1;
        if(compareInt>0)return 1;
        return 0;
    }
}
