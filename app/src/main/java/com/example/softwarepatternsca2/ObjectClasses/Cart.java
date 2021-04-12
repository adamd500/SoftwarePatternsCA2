package com.example.softwarepatternsca2.ObjectClasses;

import java.util.ArrayList;

public class Cart {

    int total;
    ArrayList<StockItem> items= new ArrayList<>();
    Customer customer ;
    boolean active;
    String cartId;
    String timeOfOrder;

    public Cart(int total, ArrayList<StockItem> items, Customer customer, boolean active, String cartId) {
        this.total = total;
        this.items = items;
        this.customer = customer;
        this.active = active;
        this.cartId = cartId;
    }

    public Cart() {

    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<StockItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<StockItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
