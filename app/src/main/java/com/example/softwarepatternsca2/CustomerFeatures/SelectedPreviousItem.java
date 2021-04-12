package com.example.softwarepatternsca2.CustomerFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectedPreviousItem extends AppCompatActivity {

    private ImageView imgView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    String itemId;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    TextView t1,t2,t3,t4;
    RatingBar rating;
    EditText review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_previous_item);

        t1=(TextView)findViewById(R.id.txtViewCategory) ;
        t2=(TextView)findViewById(R.id.txtViewManuf) ;
        t3=(TextView)findViewById(R.id.txtViewPrice) ;
        t4=(TextView)findViewById(R.id.txtViewTitle) ;
        imgView = (ImageView) findViewById(R.id.imageView);
        rating=(RatingBar)findViewById(R.id.rating);
        review=(EditText)findViewById(R.id.review);

        storage = FirebaseStorage.getInstance();
        Intent intent = getIntent();
        itemId=intent.getStringExtra("id");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        getItem();
    }

    public void getItem(){
        ref.child("StockItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem item = child.getValue(StockItem.class);
                    if (item.getItemId().equals(itemId)) {

                        t4.setText(item.getTitle());
                        t1.setText(item.getCategory());
                        t2.setText(item.getManufacturer());
                        t3.setText(String.valueOf(item.getPrice()));

                        storageReference = storage.getReferenceFromUrl("gs://softwarepatternsca2.appspot.com" + item.getImageUrl());

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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   Log.m("DBE Error","Cancel Access DB");
            }
        });
    }

    public void submitFeedback(View view) {

        ref.child("StockItem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    StockItem item = child.getValue(StockItem.class);
                    if (item.getItemId().equals(itemId)) {

                        String reviewTxt = review.getText().toString();
                       ArrayList<String>reviews=new ArrayList<>();

                       if(item.getFeedback()!=null){
                           reviews= item.getFeedback();
                       }

                       reviews.add(reviewTxt);

                       int ratingNum= (int) (item.getCustomerRating()+rating.getRating());
                       int numReviews=item.getNumOfRatings()+1;

                       item.setFeedback(reviews);
                       item.setCustomerRating(ratingNum);
                       item.setNumOfRatings(numReviews);

                       ref.child("StockItem").child(itemId).setValue(item);

                       Intent intent = new Intent(getApplicationContext(),PreviousOrders.class);
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