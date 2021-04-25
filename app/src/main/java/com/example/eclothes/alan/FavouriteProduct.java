package com.example.eclothes.alan;

import android.media.Image;
import android.widget.ImageView;

public class FavouriteProduct {
    private String url;
    private String title;
    private double price;

    public FavouriteProduct(String url, String title, double price) {
        this.url = url;
        this.title = title;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
