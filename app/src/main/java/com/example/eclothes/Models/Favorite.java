package com.example.eclothes.Models;

public class Favorite {
    private String _id;
    private User user;
    private Product product;

    public Favorite(User user, Product product) {
        this.user = user;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
