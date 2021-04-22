package com.example.eclothes.Models;

import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(tableName = "current_merchant_table")
public class CurrentMerchant {
    private String token;
    @Embedded
    private Merchant merchant;

    public CurrentMerchant(String token, Merchant merchant) {
        this.token = token;
        this.merchant = merchant;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
