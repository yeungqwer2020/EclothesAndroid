package com.example.eclothes.alan;

import android.widget.ImageView;

public class FavouriteShop {
    private String url;
    private String shopName, address;

    public FavouriteShop(String url, String shopName, String address) {
        this.url = url;
        this.shopName = shopName;
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
