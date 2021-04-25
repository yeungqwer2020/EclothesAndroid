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

public class CustomAdapter extends ArrayAdapter<FavouriteProduct> {
    private Context context;
    int resource;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FavouriteProduct> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String url = getItem(position).getUrl();
        String title = getItem(position).getTitle();
        double price = getItem(position).getPrice();

        FavouriteProduct fp = new FavouriteProduct(url, title, price);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        ImageView ivew = convertView.findViewById(R.id.imageView2);
        TextView tvTitle = convertView.findViewById(R.id.tv1);
        TextView tvPrice = convertView.findViewById(R.id.tv2);

        //ivew.setImageDrawable(iv.getDrawable());
        tvTitle.setText(title);
        tvPrice.setText("$" + String.valueOf(price));
        Picasso.get().load(url).into(ivew);


        return convertView;
    }
}
