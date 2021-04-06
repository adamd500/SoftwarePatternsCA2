package com.example.softwarepatternsca2.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca2.AdminFeatures.SelectedStockItemAdmin;
import com.example.softwarepatternsca2.CustomerFeatures.SelectedStockItem;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AllStockItemsAdminAdapter extends RecyclerView.Adapter<AllStockItemsAdminAdapter.MyViewHolder>{

    ArrayList<StockItem> stockItemsFromDb;

    //Inner class - Provide a reference to each item/row
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtView;
        public ImageView img;
        private FirebaseStorage storage;
        private StorageReference storageReference;

        public MyViewHolder(View itemView){
            super(itemView);
            txtView=(TextView)itemView.findViewById(R.id.textView);
            img=(ImageView)itemView.findViewById(R.id.imageView3);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public AllStockItemsAdminAdapter(ArrayList<StockItem>myDataset){
        stockItemsFromDb =myDataset;
    }
    @Override
    public AllStockItemsAdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //create new view - create a row - inflate the layout for the row
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.recyclerview_format,parent,false);
        AllStockItemsAdminAdapter.MyViewHolder viewHolder=new AllStockItemsAdminAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllStockItemsAdminAdapter.MyViewHolder holder, int position) {

        final StockItem name= stockItemsFromDb.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://softwarepatternsca2.appspot.com" + name.getImageUrl());
        try {
            final File file = File.createTempFile("image", "jpeg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //holder.img.setImageResource(R.drawable.listing);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.txtView.setText("\nTitle : "+name.getTitle()+"\n Category : "+name.getCategory()
                +"\n Manufacturer :"+name.getManufacturer()+"\n Price : "+name.getPrice());
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position=this.getLayoutPosition();
                StockItem selectedjob = stockItemsFromDb.get(position);
                Intent intent= new Intent(v.getContext(), SelectedStockItemAdmin.class);
                intent.putExtra("id",name.getItemId());
                intent.putExtra("title",name.getTitle());

                v.getContext().startActivity(intent);
            }
        });
    }
    public void add(int position, StockItem listing){
        stockItemsFromDb.add(position, listing);
        notifyItemInserted(position);
    }
    public void remove(int position){
        stockItemsFromDb.remove(position);
        notifyItemRemoved(position);
    }
    public void update(StockItem listing,int position){
        stockItemsFromDb.set(position,listing);
        notifyItemChanged(position);
    }
    public void addItemtoEnd(StockItem listing){
        //these functions are user-defined
        stockItemsFromDb.add(listing);
        notifyItemInserted(stockItemsFromDb.size());
    }


    @Override
    public int getItemCount() {
        return stockItemsFromDb.size();
    }

}