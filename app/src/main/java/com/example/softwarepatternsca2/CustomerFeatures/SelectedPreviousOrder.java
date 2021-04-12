package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.softwarepatternsca2.Adapters.CartAdapter;
import com.example.softwarepatternsca2.Adapters.OrderItemsAdapter;
import com.example.softwarepatternsca2.Adapters.PreviousOrderAdapterCustomer;
import com.example.softwarepatternsca2.ObjectClasses.Cart;
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

public class SelectedPreviousOrder extends AppCompatActivity {

    ArrayList<StockItem> items = new ArrayList<StockItem>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static OrderItemsAdapter myAdapter;
    private FirebaseUser user;
    String uid,orderId;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_previous_order);

        Intent intent = getIntent();
        orderId=intent.getStringExtra("id");


        t1=(TextView)findViewById(R.id.total);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new OrderItemsAdapter(items);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

        getOrder();
    }

    public void getOrder() {
        ref.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {

                    Cart cart = child.getValue(Cart.class);

                    if(cart.getCartId().equals(orderId)){
                        t1.setText(String.valueOf(cart.getTotal()));
                        int total=0;
                        for(StockItem item:cart.getItems()){
                            items.add(item);
                            total=total+item.getPrice();
                        }
                        t1.setText(String.valueOf(total));

                    }

                    myAdapter.notifyItemInserted(items.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }
}