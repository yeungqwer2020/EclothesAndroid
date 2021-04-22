package com.example.eclothes.Models;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class Merchant {
    private String _id;
    private String firstName; // required
    private String lastName; // required
    private String shopName; // required
    private String phone; // required
    private String email; // required
    private String password; // required
    private String passwordConfirm; // required
    private String username;
    @Embedded
    private Location location;
    private String photo;
    @SerializedName("description")
    private String desc;

    // login
    public Merchant(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Register
    public Merchant(String firstName, String lastName, String shopName, String phone, String email, String password, String passwordConfirm, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.shopName = shopName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.username = username;
    }

    // Current Merchant given after login/register with required fields
    public Merchant(String _id, String firstName, String lastName, String shopName, String phone, String email) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.shopName = shopName;
        this.phone = phone;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
