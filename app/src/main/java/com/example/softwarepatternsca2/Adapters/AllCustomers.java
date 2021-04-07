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

import com.example.softwarepatternsca2.AdminFeatures.SelectedCustomer;
import com.example.softwarepatternsca2.CustomerFeatures.SelectedStockItem;
import com.example.softwarepatternsca2.ObjectClasses.Customer;
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

public class AllCustomers  extends RecyclerView.Adapter<AllCustomers.MyViewHolder>{

    ArrayList<Customer> customersFromDb;

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

    public AllCustomers(ArrayList<Customer>myDataset){
        customersFromDb =myDataset;
    }
    @Override
    public AllCustomers.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //create new view - create a row - inflate the layout for the row
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.recyclerview_format,parent,false);
        AllCustomers.MyViewHolder viewHolder=new AllCustomers.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllCustomers.MyViewHolder holder, int position) {

        final Customer name= customersFromDb.get(position);
        holder.txtView.setText("\n Name : "+name.getName()+"\n Number : "+name.getPhoneNumber()
                +"\n Email :"+name.getEmail());
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position=this.getLayoutPosition();
                Customer selectedjob = customersFromDb.get(position);
                Intent intent= new Intent(v.getContext(), SelectedCustomer.class);
                intent.putExtra("id",name.getCustomerId());
                v.getContext().startActivity(intent);
            }
        });
    }
    public void add(int position, Customer listing){
        customersFromDb.add(position, listing);
        notifyItemInserted(position);
    }
    public void remove(int position){
        customersFromDb.remove(position);
        notifyItemRemoved(position);
    }
    public void update(Customer listing,int position){
        customersFromDb.set(position,listing);
        notifyItemChanged(position);
    }
    public void addItemtoEnd(Customer listing){
        //these functions are user-defined
        customersFromDb.add(listing);
        notifyItemInserted(customersFromDb.size());
    }


    @Override
    public int getItemCount() {
        return customersFromDb.size();
    }

}