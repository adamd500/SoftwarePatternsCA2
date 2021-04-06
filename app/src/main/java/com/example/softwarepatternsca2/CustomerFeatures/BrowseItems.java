package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
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
import java.util.stream.Collectors;

public class BrowseItems extends AppCompatActivity implements RecyclerViewState {

    ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static AllStockItemsAdapter myAdapter;
    private FirebaseUser user;
    String uid;
    Spinner filters,order;
    int filterSelected;
    int orderSelected;
    String keyword;
    EditText e1;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        ascending();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void ascending() {



        //orderSelected=order.getSelectedItemPosition();
        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterSelected = filters.getSelectedItemPosition();
                if(filterSelected==0){
                    stockItems.clear();
                    getItems("all");
                }

                if(filterSelected==1){

                    List<StockItem>ascendingItems=stockItems.stream().sorted((o1, o2) -> o1.compareCategory(o2)).collect(Collectors.toList());
                    myAdapter.notifyDataSetChanged();
                    stockItems.clear();
                    for(StockItem item : ascendingItems){
                        stockItems.add(item);
                    }
                    myAdapter.notifyDataSetChanged();
                    myAdapter.notifyItemInserted(stockItems.size() - 1);

                }
                if(filterSelected==2){

                    // byManufacturer();
                    List<StockItem>ascendingItems=stockItems.stream().sorted((o1, o2) -> o1.compareManufacture(o2)).collect(Collectors.toList());
                    myAdapter.notifyDataSetChanged();
                    stockItems.clear();
                    for(StockItem item : ascendingItems){
                        stockItems.add(item);
                    }
                    myAdapter.notifyDataSetChanged();
                    myAdapter.notifyItemInserted(stockItems.size() - 1);
                }

                if(filterSelected==3){

                    List<StockItem>ascendingItems=stockItems.stream().sorted((o1, o2) -> o1.compareTitle(o2)).collect(Collectors.toList());
                    myAdapter.notifyDataSetChanged();
                    stockItems.clear();
                    for(StockItem item : ascendingItems){
                        stockItems.add(item);
                    }
                    myAdapter.notifyDataSetChanged();
                    myAdapter.notifyItemInserted(stockItems.size() - 1);

                }

                if(filterSelected==4){

                    List<StockItem>ascendingItems=stockItems.stream().sorted((o1, o2) -> o1.comparePrice(o2)).collect(Collectors.toList());
                    myAdapter.notifyDataSetChanged();
                    stockItems.clear();
                    for(StockItem item : ascendingItems){
                        stockItems.add(item);
                    }
                    myAdapter.notifyDataSetChanged();
                    myAdapter.notifyItemInserted(stockItems.size() - 1);        }

            }
         @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
    }

    @Override
    public void descending() {

    }


    public void getItems(String filter) {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);
                    if (!stockItem.isRemoved()&&stockItem.getStockAmount()>0) {
                        if (filter.equalsIgnoreCase("all")) {
                            stockItems.add(stockItem);
                        }

                        myAdapter.notifyItemInserted(stockItems.size() - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filter(View view) {

        keyword=e1.getText().toString();



    }
}