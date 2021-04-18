package com.example.softwarepatternsca2.AdminFeatures;

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
import com.example.softwarepatternsca2.Adapters.AllStockItemsAdminAdapter;
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

public class ViewStockedItems extends AppCompatActivity implements RecyclerViewState {

    ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
    private FirebaseDatabase database;
    private DatabaseReference ref;
    static AllStockItemsAdminAdapter myAdapter;
    private FirebaseUser user;
    String uid;
    Spinner filters, order;
    int filterSelected;
    int orderSelected;
    String keyword;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stocked_items);

        filters = findViewById(R.id.filter);
        e1 = findViewById(R.id.variant);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recylcerViewProf);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        getItems();

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new AllStockItemsAdminAdapter(stockItems);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);

        getItems();
    }

    public void getItems() {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {

                    StockItem stockItem = child.getValue(StockItem.class);

                    if (!stockItem.isRemoved()) {
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

        stockItems.clear();
        myAdapter.notifyDataSetChanged();

        keyword = e1.getText().toString();
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem stockItem = child.getValue(StockItem.class);
                    if ((!stockItem.isRemoved()) && (stockItem.getStockAmount() > 0) && (stockItem.getTitle().contains(keyword)) || (stockItem.getCategory().contains(keyword))
                            || stockItem.getManufacturer().equalsIgnoreCase(keyword)) {

                        stockItems.add(stockItem);
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

    @Override
    public void ascending() {

        filterSelected = filters.getSelectedItemPosition();

        if (filterSelected == 0) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                }
            });
            myAdapter.notifyDataSetChanged();
        }

        if (filterSelected == 1) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o1.getManufacturer().compareToIgnoreCase(o2.getManufacturer());
                }
            });
            myAdapter.notifyDataSetChanged();


        }
        if (filterSelected == 2) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                }
            });
            myAdapter.notifyDataSetChanged();

        }

        if (filterSelected == 3) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return String.valueOf(o1.getPrice()).compareToIgnoreCase(String.valueOf(o2.getPrice()));
                }
            });
            myAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void descending() {

        filterSelected = filters.getSelectedItemPosition();
        if (filterSelected == 0) {
            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o2.getCategory().compareToIgnoreCase(o1.getCategory());
                }
            });
            myAdapter.notifyDataSetChanged();
        }

        if (filterSelected == 1) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o2.getManufacturer().compareToIgnoreCase(o1.getManufacturer());
                }
            });
            myAdapter.notifyDataSetChanged();


        }
        if (filterSelected == 2) {

            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return o2.getTitle().compareToIgnoreCase(o1.getTitle());
                }
            });
            myAdapter.notifyDataSetChanged();
        }

        if (filterSelected == 3) {
            Collections.sort(stockItems, new Comparator<StockItem>() {
                @Override
                public int compare(StockItem o1, StockItem o2) {
                    return String.valueOf(o2.getPrice()).compareToIgnoreCase(String.valueOf(o1.getPrice()));
                }
            });
            myAdapter.notifyDataSetChanged();
        }


    }
}