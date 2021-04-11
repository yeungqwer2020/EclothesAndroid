package com.example.eclothes.Models;

public class Comment {
    private String _id;
    private Product product;
    private String comment;
    private String user;
    private String createAt;

    public Comment(Product product, String comment, String user) {
        this.product = product;
        this.comment = comment;
        this.user = user;
    }

    public Comment(Product product, String comment, String user, String createAt) {
        this.product = product;
        this.comment = comment;
        this.user = user;
        this.createAt = createAt;
    }



}
