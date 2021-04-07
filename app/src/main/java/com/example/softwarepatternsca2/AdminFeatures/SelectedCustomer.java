package com.example.softwarepatternsca2.AdminFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softwarepatternsca2.Adapters.AdminPreviousOrderAdapter;
import com.example.softwarepatternsca2.Adapters.AllCustomers;
import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.Customer;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedCustomer extends AppCompatActivity {

    ArrayList<Cart> orders = new ArrayList<Cart>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    TextView t1,t2,t3,t4,t5,t6;
     String customerId;
     AdminPreviousOrderAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_customer);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new AdminPreviousOrderAdapter(orders);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.email);
        t3=(TextView)findViewById(R.id.address);
        t4=(TextView)findViewById(R.id.numOfOrders);
        t5=(TextView)findViewById(R.id.number);
        t6=(TextView)findViewById(R.id.title);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        Intent intent = getIntent();
        customerId=intent.getStringExtra("id");
        t6.setText("Customer : "+customerId);

        getCustomer();
    }

    public void getCustomer() {
        ref.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Customer customer = child.getValue(Customer.class);
                    if (customer.getType().equalsIgnoreCase("Customer")) {
                        if (customer.getCustomerId().equals(customerId)) {
                            t1.setText("Name : " + customer.getName());
                            t2.setText("Email : " + customer.getEmail());
                            t3.setText("Address : " + customer.getAddress());
                            t4.setText("Phone Number : " + customer.getPhoneNumber());
                            t5.setText("Number of Orders : " + String.valueOf(customer.getNumOfOrders()));

                            getOrders();
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

    public void getOrders() {
        ref.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);

                    if (cart.getCustomer().getCustomerId().equals(customerId)&& !cart.isActive()) {
                        orders.add(cart);
                        myAdapter.notifyItemInserted(orders.size() - 1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

}