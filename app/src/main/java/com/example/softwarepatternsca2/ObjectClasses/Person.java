package com.example.softwarepatternsca2.ObjectClasses;

import com.example.softwarepatternsca2.Intefaces.PersonType;

public class Person {

    String name;
    String phoneNumber;
    String address;
    String eircode;
    String email;
    String type;

    public Person(String name, String phoneNumber, String address, String eircode, String email, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.eircode = eircode;
        this.email = email;
        this.type = type;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
