package com.example.eclothes;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private RecyclerViewClickListener listener;

    ArrayList<EntityNews> entityNewsArrayList;
    Context context;


    public CustomAdapter(ArrayList<EntityNews> entityNewsArrayList, Context context, RecyclerViewClickListener listener) {
        this.entityNewsArrayList = entityNewsArrayList;
        this.context = context;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView product_iamge;
        TextView product_name;
        TextView product_price;
        TextView product_description;

        MyViewHolder (View itemview){
            super(itemview);

            this.product_iamge=(ImageView) itemview.findViewById(R.id.product_image);
            this.product_name=(TextView) itemview.findViewById(R.id.product_name);
            this.product_price=(TextView) itemview.findViewById(R.id.product_price);
            this.product_description=(TextView) itemview.findViewById(R.id.product_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.entity_news, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageView product_image=holder.product_iamge;
        TextView product_name=holder.product_name;
        TextView product_price=holder.product_price;
        TextView product_description=holder.product_description;

        String url=entityNewsArrayList.get(position).image;
        product_name.setText(entityNewsArrayList.get(position).productname+"");
        product_price.setText("$ "+entityNewsArrayList.get(position).productprice+"");
        product_description.setText(entityNewsArrayList.get(position).productdescription+"");

        if (url.compareTo("[]")==0)
            Picasso.get().load(R.drawable.clothes).into(product_image);
        else
            Picasso.get().load(url).into(product_image);
    }

    @Override
    public int getItemCount() {
        return entityNewsArrayList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
