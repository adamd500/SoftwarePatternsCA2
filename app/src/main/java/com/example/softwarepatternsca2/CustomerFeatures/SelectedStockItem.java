package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softwarepatternsca2.Adapters.FeedbackAdapter;
import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectedStockItem extends AppCompatActivity {
    private TextView t1, t2, t3, t4;
    private ImageView imgView;
    private RatingBar t5;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;
    private String stockItemId;
    private StockItem stockItem;
    private String photoUrl,uid;
    ArrayList<String> feedbackComments=new ArrayList<>();
    FeedbackAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_stock_item);

        t1 = (TextView) findViewById(R.id.txtViewTitle);
        t2 = (TextView) findViewById(R.id.txtViewCategory);
        t3 = (TextView) findViewById(R.id.txtViewManuf);
        t4 = (TextView) findViewById(R.id.txtViewPrice);
        t5=(RatingBar)findViewById(R.id.txtViewRatings);
        imgView = (ImageView) findViewById(R.id.imageView);

        storage = FirebaseStorage.getInstance();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        RecyclerView mRecyclerView=(RecyclerView)findViewById(R.id.recylcerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter= new FeedbackAdapter(feedbackComments);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        Intent i = getIntent();
        stockItemId = i.getStringExtra("id");

        getListing();
    }
    public void getListing() {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    if (child.getKey().equals(stockItemId)) {

                        stockItem = child.getValue(StockItem.class);
                        if(stockItem.getFeedback()!=null){

                            for(String comment:stockItem.getFeedback()){

                                feedbackComments.add(comment);
                            }

                            myAdapter.notifyItemInserted(feedbackComments.size() - 1);
                        }
                        if(stockItem.getNumOfRatings()!=0&&stockItem.getCustomerRating()!=0){

                            int average=stockItem.getCustomerRating()/stockItem.getNumOfRatings();

                            t5.setRating(average);
                        }
                        photoUrl = stockItem.getImageUrl();
                        displayListing();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }


    public void displayListing() {
        t1.setText(stockItem.getTitle());
        t2.setText("Category : " + stockItem.getCategory());
        t3.setText("Manufacturer : " + stockItem.getManufacturer());
        t4.setText("Price : " + stockItem.getPrice());

         storageReference = storage.getReferenceFromUrl("gs://softwarepatternsca2.appspot.com" + photoUrl);

//fypdatabase-d9dfe.appspot.com/images
        try {
            final File file = File.createTempFile("image", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imgView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Image Failed to Load", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToCart(View view) {
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {

                    StockItem item = child.getValue(StockItem.class);
                    if (item.getItemId().equals(stockItemId)) {
                        add(item);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }
    public void add(StockItem stockItem){
        ref.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    Cart cart = child.getValue(Cart.class);
                    if (cart.getCustomer().getCustomerId().equals(uid)&& cart.isActive()) {

                        if(cart.getItems()==null){
                            ArrayList<StockItem>items = new ArrayList<>();
                            items.add(stockItem);
                            cart.setItems(items);
                            ref.child("Cart").child(cart.getCartId()).setValue(cart);

                            Intent intent = new Intent(getApplicationContext(),WelcomeCustomer.class);
                            startActivity(intent);
                        }else{
                            ArrayList<StockItem>existingItems=cart.getItems();
                            existingItems.add(stockItem);
                            cart.setItems(existingItems);
                            ref.child("Cart").child(cart.getCartId()).setValue(cart);
                            Intent intent = new Intent(getApplicationContext(),WelcomeCustomer.class);
                            startActivity(intent);
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
}