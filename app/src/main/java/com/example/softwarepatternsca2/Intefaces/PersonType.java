package com.example.softwarepatternsca2.Intefaces;

import java.io.Serializable;

//Strategy Pattern
public interface PersonType {

    public String type();

     class isAdmin implements PersonType {
        @Override
        public String type() {
            return "Admin";
        }

    }

     class isCustomer implements PersonType {
        @Override
        public String type() {
            return "Customer";
        }

    }
}
