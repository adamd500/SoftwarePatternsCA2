package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.softwarepatternsca2.Adapters.AllCustomers;
import com.example.softwarepatternsca2.Adapters.PreviousOrderAdapterCustomer;
import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.Customer;
import com.example.softwarepatternsca2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreviousOrders extends AppCompatActivity {

    ArrayList<Cart> orders = new ArrayList<Cart>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static PreviousOrderAdapterCustomer myAdapter;
    private FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_feedback);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new PreviousOrderAdapterCustomer(orders);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

        getCustomers();
    }

    public void getCustomers() {
        ref.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);

                    if (cart.getCustomer().getCustomerId().equals(uid) && !cart.isActive()) {
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