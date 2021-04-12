package com.example.softwarepatternsca2.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca2.CustomerFeatures.SelectedPreviousOrder;
import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PreviousOrderAdapterCustomer extends RecyclerView.Adapter<PreviousOrderAdapterCustomer.MyViewHolder>{

    ArrayList<Cart> ordersFromDb;

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

    public PreviousOrderAdapterCustomer(ArrayList<Cart>myDataset){
        ordersFromDb =myDataset;
    }
    @Override
    public PreviousOrderAdapterCustomer.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //create new view - create a row - inflate the layout for the row
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.recyclerview_format,parent,false);
        PreviousOrderAdapterCustomer.MyViewHolder viewHolder=new PreviousOrderAdapterCustomer.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousOrderAdapterCustomer.MyViewHolder holder, int position) {

        final Cart name= ordersFromDb.get(position);

        holder.txtView.setText("\nOrder ID : "+name.getCartId()+"\n Order Total : "+name.getTotal()+"\nDate of Order :"+name.getTimeOfOrder());
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position=this.getLayoutPosition();
                Intent intent = new Intent(v.getContext(), SelectedPreviousOrder.class);
                intent.putExtra("id",name.getCartId());
                v.getContext().startActivity(intent);
            }
        });
    }
    public void add(int position, Cart listing){
        ordersFromDb.add(position, listing);
        notifyItemInserted(position);
    }
    public void remove(int position){
        ordersFromDb.remove(position);
        notifyItemRemoved(position);
    }
    public void update(Cart listing,int position){
        ordersFromDb.set(position,listing);
        notifyItemChanged(position);
    }
    public void addItemtoEnd(Cart listing){
        //these functions are user-defined
        ordersFromDb.add(listing);
        notifyItemInserted(ordersFromDb.size());
    }


    @Override
    public int getItemCount() {
        return ordersFromDb.size();
    }

}
