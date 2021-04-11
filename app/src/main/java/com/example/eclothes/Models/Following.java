package com.example.eclothes.Models;

public class Following {
    private String _id;
    private User user;
    private Merchant merchant;

    public Following(User user, Merchant merchant) {
        this.user = user;
        this.merchant = merchant;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
