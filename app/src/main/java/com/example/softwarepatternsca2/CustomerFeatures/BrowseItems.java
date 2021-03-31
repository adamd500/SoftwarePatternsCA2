package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.softwarepatternsca2.Adapters.AllStockItemsAdapter;
import com.example.softwarepatternsca2.Intefaces.RecyclerViewState;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BrowseItems extends AppCompatActivity implements RecyclerViewState {

    ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
    List<StockItem> sortedByPrice=new ArrayList<StockItem>();

    private FirebaseDatabase database;
    private DatabaseReference ref;

    AllStockItemsAdapter myAdapter;
    private FirebaseUser user;
    String uid;
    Spinner filters,order;
    int filterSelected;
    int orderSelected;
    String keyword;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        filters=findViewById(R.id.filter);
        order=findViewById(R.id.order);
        e1=findViewById(R.id.variant);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        getItems("all");

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new AllStockItemsAdapter(stockItems);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
    }


    @Override
    public void ascending() {

        Collections.sort(stockItems, new Comparator<StockItem>() {
            @Override
            public int compare(StockItem o1, StockItem o2) {
                return Integer.valueOf(o1.getPrice()).compareTo(Integer.valueOf(o2.getPrice()));
            }
        });
        //stockItems.sort();
    }

    @Override
    public void descending() {

    }

    @Override
    public void byCategory() {

      //  ArrayList<StockItem>selectedCategory=new ArrayList<>();
        myAdapter.notifyDataSetChanged();
        stockItems.clear();
        myAdapter.notifyDataSetChanged();

        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);

                    if(stockItem.getCategory().contains(keyword)||stockItem.getCategory().equalsIgnoreCase(keyword)){
                        stockItems.add(stockItem);
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

    @Override
    public void byPrice() {

        if(orderSelected==0){
            ascending();
        }

    }

    @Override
    public void byManufacturer() {
        myAdapter.notifyDataSetChanged();
        stockItems.clear();
        myAdapter.notifyDataSetChanged();

        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);

                    if(keyword.equalsIgnoreCase(stockItem.getManufacturer())){
                        stockItems.add(stockItem);
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

    @Override
    public void byTitle() {

        myAdapter.notifyDataSetChanged();
        stockItems.clear();
        myAdapter.notifyDataSetChanged();

        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);

                    if(stockItem.getTitle().contains(keyword)||stockItem.getTitle().equalsIgnoreCase(keyword)){
                        stockItems.add(stockItem);
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

    public void getItems(String filter) {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);

                    if(filter.equalsIgnoreCase("all")){
                        stockItems.add(stockItem);
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

    public void filter(View view) {

        keyword=e1.getText().toString();
        orderSelected=order.getSelectedItemPosition();
        filterSelected=filters.getSelectedItemPosition();



        if(filterSelected==0){
            getItems("all");
        }
        if(filterSelected==1){
            byCategory();
        }
        if(filterSelected==2){
            byManufacturer();
        }
        if(filterSelected==3){
            byTitle();
        }

        if(filterSelected==4){
            byPrice();
        }


        if(orderSelected==0){
             ascending();
        }
        if(orderSelected==1){
            // descending();
        }
//         <item>All Items</item>
//        <item>Category</item>
//        <item>Manufacturer</item>
//        <item>Title</item>
//        <item>Price</item>
    }
}