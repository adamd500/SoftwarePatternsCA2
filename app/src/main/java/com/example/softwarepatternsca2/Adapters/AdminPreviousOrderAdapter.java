package com.example.softwarepatternsca2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca2.ObjectClasses.Cart;
import com.example.softwarepatternsca2.ObjectClasses.StockItem;
import com.example.softwarepatternsca2.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminPreviousOrderAdapter  extends RecyclerView.Adapter<AdminPreviousOrderAdapter.MyViewHolder> {

    ArrayList<Cart> ordersFromDb;

    //Inner class - Provide a reference to each item/row
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtView;
        public ImageView img;
        private FirebaseStorage storage;
        private StorageReference storageReference;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.textView);
            img = (ImageView) itemView.findViewById(R.id.imageView3);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public AdminPreviousOrderAdapter(ArrayList<Cart> myDataset) {
        ordersFromDb = myDataset;
    }

    @Override
    public AdminPreviousOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new view - create a row - inflate the layout for the row
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recyclerview_format, parent, false);
        AdminPreviousOrderAdapter.MyViewHolder viewHolder = new AdminPreviousOrderAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPreviousOrderAdapter.MyViewHolder holder, int position) {

        final Cart name = ordersFromDb.get(position);


        holder.txtView.setText("\nOrder Total : " + name.getTotal() + "\n Order ID : " + name.getCartId());
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position=this.getLayoutPosition();
//                StockItem selectedjob = stockItemsFromDb.get(position);
//                Intent intent= new Intent(v.getContext(), SelectedStockItem.class);
//                intent.putExtra("id",name.getItemId());
//                intent.putExtra("title",name.getTitle());
//
//                v.getContext().startActivity(intent);
            }
        });
    }

    public void add(int position, Cart listing) {
        ordersFromDb.add(position, listing);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        ordersFromDb.remove(position);
        notifyItemRemoved(position);
    }

    public void update(Cart listing, int position) {
        ordersFromDb.set(position, listing);
        notifyItemChanged(position);
    }

    public void addItemtoEnd(Cart listing) {
        //these functions are user-defined
        ordersFromDb.add(listing);
        notifyItemInserted(ordersFromDb.size());
    }


    @Override
    public int getItemCount() {
        return ordersFromDb.size();
    }
}
