package com.example.eclothes.API;

public class UserStaticInformation {
    //user id
    private static String userId;
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

    public static String getRole() {
        return role;
    }

    public static void setRole(String userRole) {
        role = userRole;
    }
}
