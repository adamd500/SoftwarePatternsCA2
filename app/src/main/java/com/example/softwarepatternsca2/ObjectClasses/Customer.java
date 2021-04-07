package com.example.softwarepatternsca2.ObjectClasses;

import com.example.softwarepatternsca2.Intefaces.PersonType;

public class Customer extends Person{

    String customerId;
    String cardNumber;
    String securityCode;
    String cardExpiry;
    boolean newUserDiscountUsed;
    int numOfOrders;
    boolean hasNewCart;

    public Customer(){

    }

    public Customer(String name, String phoneNumber, String address, String eircode, String email, String type, String customerId,
                    String cardNumber, String securityCode, String cardExpiry, boolean newUserDiscountUsed, int numOfOrders, boolean hasNewCart) {
        super(name, phoneNumber, address, eircode, email, type);
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.cardExpiry = cardExpiry;
        this.newUserDiscountUsed = newUserDiscountUsed;
        this.numOfOrders = numOfOrders;
        this.hasNewCart = hasNewCart;
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

    public boolean isHasNewCart() {
        return hasNewCart;
    }

    public void setHasNewCart(boolean hasNewCart) {
        this.hasNewCart = hasNewCart;
    }
}
