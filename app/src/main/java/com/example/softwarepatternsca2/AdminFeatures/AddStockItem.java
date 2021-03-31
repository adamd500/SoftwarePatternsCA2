package com.example.softwarepatternsca2.AdminFeatures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class AddStockItem extends AppCompatActivity {

    private DatabaseReference dbRef;
    private ImageView ivImage;
    private static final String StockItem = "StockItem";
    private static final int PICK_IMAGE_REQUEST =22 ;
    private EditText e1, e2,e3,e4,e5;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;
    private String uid;
    private Uri filePath;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_item);

        dbRef= FirebaseDatabase.getInstance().getReference(StockItem);
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        ivImage = (ImageView) findViewById(R.id.imageView);
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
    }

    public void postItem(View v) {
        e1 = (EditText) findViewById(R.id.editTextTitle);
        e2 = (EditText) findViewById(R.id.editCategory);
        e3=(EditText)findViewById(R.id.editManufacture) ;
        e4 = (EditText) findViewById(R.id.editPrice);
        e5 = (EditText) findViewById(R.id.stockAmount);

        uploadImage();
        String keyId = dbRef.push().getKey();


        String title = e1.getText().toString();
        String category = e2.getText().toString();
        String manufacturer = e3.getText().toString();
        int price=Integer.parseInt(e4.getText().toString());
        int stockNum=Integer.parseInt(e5.getText().toString());
        String itemId=keyId;
        String imageUrl=picPath;

        if((title.isEmpty())|| (manufacturer.isEmpty())||(e4.getText().toString().isEmpty())||(category.isEmpty())||(imageUrl.isEmpty())||(itemId.isEmpty())){

            Toast.makeText(getApplicationContext(), "Error : All fields must be filled ", Toast.LENGTH_SHORT).show();

        }else{
            StockItem stockItem= new StockItem( title,  manufacturer,  price,  category,  imageUrl,  itemId,stockNum);
            dbRef.child(keyId).setValue(stockItem);

            Toast.makeText(getApplicationContext(), "Item successfully created ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), WelcomeAdmin.class);
            startActivity(intent);
        }



    }

    //
    //
    //METHODS FOR ADDING IMAGE
    //
    //
    public void selectImage(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            picPath = ref.getPath();
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    // dbRef.child("User").child(uid).child("profileImageUri").setValue(filePath);

                                    Toast.makeText(getApplicationContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
        }
    }



}