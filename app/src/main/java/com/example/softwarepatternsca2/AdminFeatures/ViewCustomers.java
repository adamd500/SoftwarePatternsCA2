package com.example.softwarepatternsca2.AdminFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.softwarepatternsca2.Adapters.AllCustomers;
import com.example.softwarepatternsca2.Adapters.AllStockItemsAdminAdapter;
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

public class ViewCustomers extends AppCompatActivity {


    ArrayList<Customer> customers = new ArrayList<Customer>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static AllCustomers myAdapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new AllCustomers(customers);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

        getCustomers();

    }

    public void getCustomers() {
        ref.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Customer customer = child.getValue(Customer.class);
                    if (customer.getType().equalsIgnoreCase("Customer")) {
                        customers.add(customer);

                    }

                    myAdapter.notifyItemInserted(customers.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }
}