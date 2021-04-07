package com.example.softwarepatternsca2.ObjectClasses;

import com.example.softwarepatternsca2.Intefaces.PersonType;

public class Admin extends Person{

    String adminId;

    public Admin(){

    }

    public Admin(String name, String phoneNumber, String address, String eircode, String email, String type, String adminId) {
        super(name, phoneNumber, address, eircode, email, type);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
