package com.example.eclothes.alan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eclothes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterShop extends ArrayAdapter<FavouriteShop> {
    private Context context;
    int resource;


    public CustomAdapterShop(@NonNull Context context, int resource, @NonNull ArrayList<FavouriteShop> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String url = getItem(position).getUrl();
        String shopName = getItem(position).getShopName();
        String address = getItem(position).getAddress();

        FavouriteShop fs = new FavouriteShop(url, shopName, address);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView ivew = convertView.findViewById(R.id.imageView2);
        TextView tvShopName = convertView.findViewById(R.id.tv1);
        TextView tvAddress = convertView.findViewById(R.id.tv2);

        //ivew.setImageDrawable(iv.getDrawable());
        tvShopName.setText(shopName);
        tvAddress.setText(address);
        Picasso.get().load(url).into(ivew);

        return convertView;
    }
}
