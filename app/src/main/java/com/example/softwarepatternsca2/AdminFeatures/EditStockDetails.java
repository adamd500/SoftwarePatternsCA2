package com.example.softwarepatternsca2.AdminFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditStockDetails extends AppCompatActivity {

    private TextView t1, t2, t3, t4,t5;
    EditText e1,e2,e3,e4,e5;
    private ImageView imgView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String stockItemId;
    String title,category,manuf,price,stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock_details);

        t1=(TextView)findViewById(R.id.currentTitle);
        t2=(TextView)findViewById(R.id.currentCategory);
        t3=(TextView)findViewById(R.id.currentMan);
        t4=(TextView)findViewById(R.id.currentPrice);
        t5=(TextView)findViewById(R.id.currentStock);

        e1=(EditText)findViewById(R.id.editTextTitle);
        e2=(EditText)findViewById(R.id.editCategory);
        e3=(EditText)findViewById(R.id.editManufacture);
        e4=(EditText)findViewById(R.id.editPrice);
        e5=(EditText)findViewById(R.id.stockAmount);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        Intent intent = getIntent();
        stockItemId=intent.getStringExtra("id");

        getItem();
    }


    public void getItem() {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    if (child.getKey().equals(stockItemId)) {
                        StockItem stockItem = child.getValue(StockItem.class);

                        t1.setText(stockItem.getTitle());
                        t2.setText(stockItem.getCategory());
                        t3.setText(stockItem.getManufacturer());
                        t4.setText(String.valueOf(stockItem.getPrice()));
                        t5.setText(String.valueOf(stockItem.getStockAmount()));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    public void updateItem(View view) {

        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    if (child.getKey().equals(stockItemId)) {
                        StockItem stockItem = child.getValue(StockItem.class);

                        title=e1.getText().toString();
                        category=e2.getText().toString();
                        manuf=e3.getText().toString();
                        price=e4.getText().toString();
                        stock=e5.getText().toString();

                        if(!title.isEmpty()){
                            stockItem.setTitle(title);
                        }
                        if(!category.isEmpty()){
                            stockItem.setCategory(category);
                        }
                        if(!manuf.isEmpty()){
                            stockItem.setManufacturer(manuf);
                        }
                        if(!price.isEmpty()){
                            stockItem.setPrice(Integer.valueOf(price));
                        }
                        if(!stock.isEmpty()){
                            stockItem.setStockAmount(Integer.valueOf(stock));
                        }

                        ref.child("StockItem").child(stockItemId).setValue(stockItem);

                        Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getApplicationContext(),WelcomeAdmin.class);
                        startActivity(intent);
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