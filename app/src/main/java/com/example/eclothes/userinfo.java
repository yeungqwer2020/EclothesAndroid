package com.example.eclothes;

public class userinfo {
    String image;
    String firstname;
    String lastname;
    String username;
    String phonenumber;
    String sex;
    String email;

    public userinfo(String image, String firstname, String lastname, String username, String phonenumber, String sex,  String email) {
        this.image = image;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phonenumber = phonenumber;
        this.sex = sex;
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
