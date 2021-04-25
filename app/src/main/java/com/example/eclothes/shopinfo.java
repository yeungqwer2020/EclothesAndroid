package com.example.eclothes;

public class shopinfo {
    String image;
    String shopname;
    String username;
    String address;
    String district;
    String phonenumber;
    String openinghours;
    String email;

    public shopinfo(String image, String shopname, String username, String address, String district, String phonenumber, String openinghours, String email) {
        this.image = image;
        this.shopname = shopname;
        this.username = username;
        this.address = address;
        this.district = district;
        this.phonenumber = phonenumber;
        this.openinghours = openinghours;
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getOpeninghours() {
        return openinghours;
    }

    public void setOpeninghours(String openinghours) {
        this.openinghours = openinghours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
