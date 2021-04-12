package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.Customer;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WelcomeCustomer extends AppCompatActivity {

    private FirebaseUser user;
    String uid;
    private FirebaseDatabase database;
    private DatabaseReference ref,ref2;
    ArrayList<StockItem> items= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_customer);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        ref2=database.getReference("Cart");
        getCustomer();
    }

    private void getCustomer() {
        ref.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    if(child.getKey().equals(uid)){

                        Customer customer = child.getValue(Customer.class);

                        if(customer.getType().equalsIgnoreCase("Customer")) {

                            if (!customer.isHasNewCart()) {

                                String keyId = ref2.push().getKey();
                                //  items.add(new StockItem());
                                Cart cart = new Cart(0, items, customer, true, keyId);
                                ref2.child(keyId).setValue(cart);
                                ref.child("User").child(uid).child("hasNewCart").setValue(true);
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    public void viewItems(View view) {
        Intent intent = new Intent(this,BrowseItems.class);
        startActivity(intent);
    }

    public void viewCart(View view) {
        Intent intent = new Intent(this,ViewCart.class);
        startActivity(intent);
    }

    public void previousOrders(View view) {
        Intent intent = new Intent(this,PreviousOrders.class);
        startActivity(intent);
    }
}