package com.example.softwarepatternsca2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca2.R;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    ArrayList<String> feedbackFromDB;

    //Inner class - Provide a reference to each item/row
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtView;

        public MyViewHolder(View itemView){
            super(itemView);
            txtView=(TextView)itemView.findViewById(R.id.textView);

        }

        @Override
        public void onClick(View view) {

//            int position=this.getLayoutPosition();
//            Listing selectedListing =listingsFromDB.get(position);
//            Intent intent= new Intent(view.getContext(),SelectedListing.class);
//            view.getContext().startActivity(intent);
        }
    }

    public FeedbackAdapter(ArrayList<String>myDataset){
        feedbackFromDB =myDataset;
    }
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //create new view - create a row - inflate the layout for the row
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.row,parent,false);
        FeedbackAdapter.MyViewHolder viewHolder=new FeedbackAdapter.MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {

        final String name= feedbackFromDB.get(position);
        holder.txtView.setText("\n"+"   "+name+"\n");
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //int position=this.getLayoutPosition();
//                Listing selectedListing = feedbackFromDB.get(position);
//                Intent intent= new Intent(v.getContext(), SelectedListing.class);
//                intent.putExtra("id",name.getListingId());
//                v.getContext().startActivity(intent);
            }
        });
    }
    public void add(int position, String listing){
        feedbackFromDB.add(position, listing);
        notifyItemInserted(position);
    }
    public void remove(int position){
        feedbackFromDB.remove(position);
        notifyItemRemoved(position);
    }
    public void update(String listing,int position){
        feedbackFromDB.set(position,listing);
        notifyItemChanged(position);
    }
    public void addItemtoEnd(String listing){
        //these functions are user-defined
        feedbackFromDB.add(listing);
        notifyItemInserted(feedbackFromDB.size());
    }


    @Override
    public int getItemCount() {
        return feedbackFromDB.size();
    }

}