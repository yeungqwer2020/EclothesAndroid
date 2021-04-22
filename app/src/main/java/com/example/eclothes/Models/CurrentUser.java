package com.example.eclothes.Models;

import androidx.room.Entity;

@Entity(tableName = "current_user_table")
public class CurrentUser {
    private String token;
    private User user;

    public CurrentUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
