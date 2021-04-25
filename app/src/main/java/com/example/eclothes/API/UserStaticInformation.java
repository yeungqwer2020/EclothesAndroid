package com.example.eclothes.API;

import com.example.eclothes.R;
import com.squareup.picasso.Picasso;

public class UserStaticInformation {
    //user id
    private static String userId;
    //user name
    private static String userName;
    //user image url
    private static String userImageUrl;
    //user role
    private static String role;

    public UserStaticInformation() {

    }

    public UserStaticInformation(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserImageUrl() {
        return userImageUrl;
    }

    public static void setUserImageUrl(String image) {
        userImageUrl = "http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/" + UserStaticInformation.getRole().toLowerCase() + "s/" + UserStaticInformation.getUserId() + "/" + image;
    }

    public static String getUserName() { return userName; }

    public static void setUserName(String name) { userName = name; }

    public static String getRole() {
        return role;
    }

    public static void setRole(String userRole) {
        role = userRole;
    }
}
