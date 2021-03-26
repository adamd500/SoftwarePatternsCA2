package com.example.softwarepatternsca2.ObjectClasses;

public class Customer {

    String name;
    String address;
    String eircode;
    String email;
    String phoneNumber;
    String customerId;
    String cardNumber;
    String securityCode;
    String cardExpiry;
    boolean newUserDiscountUsed;
    int numOfOrders;


    public Customer(){

    }

    public Customer(String name, String address, String eircode, String email, String phoneNumber,
                    String customerId, String cardNumber, String securityCode, String cardExpiry, boolean newUserDiscountUsed, int numOfOrders) {
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.cardExpiry = cardExpiry;
        this.newUserDiscountUsed = newUserDiscountUsed;
        this.numOfOrders = numOfOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public boolean isNewUserDiscountUsed() {
        return newUserDiscountUsed;
    }

    public void setNewUserDiscountUsed(boolean newUserDiscountUsed) {
        this.newUserDiscountUsed = newUserDiscountUsed;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public void setNumOfOrders(int numOfOrders) {
        this.numOfOrders = numOfOrders;
    }
}
