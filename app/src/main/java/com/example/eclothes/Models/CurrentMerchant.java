package com.example.eclothes.Models;

public class CurrentMerchant {
    private String token;
    private com.example.eclothes.Models.Merchant merchant;

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
