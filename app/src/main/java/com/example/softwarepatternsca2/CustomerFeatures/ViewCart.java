package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softwarepatternsca2.Adapters.AllStockItemsAdapter;
import com.example.softwarepatternsca2.Adapters.CartAdapter;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewCart extends AppCompatActivity {
    ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static CartAdapter myAdapter;
    private FirebaseUser user;
    String uid;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        t1=(TextView)findViewById(R.id.textViewTotal);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new CartAdapter(stockItems);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
        getCart();
    }

    public void getCart() {
        ref.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);

                    if(cart.getCustomer().getCustomerId().equals(uid)&&cart.isActive()){
                        int total=0;
                        for(StockItem item:cart.getItems()){
                            stockItems.add(item);
                            total=total+item.getPrice();
                        }
                        t1.setText(String.valueOf(total));

                    }

                    myAdapter.notifyItemInserted(stockItems.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    public void pay(View view) {
        Intent intent = new Intent(getApplicationContext(),PaymentPage.class);
        startActivity(intent);
    }
}