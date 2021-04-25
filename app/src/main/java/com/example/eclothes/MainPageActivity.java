package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.search.SearchActivity;
import com.squareup.picasso.Picasso;

public class MainPageActivity extends AppCompatActivity {

    //Initialize variables
    DrawerLayout drawerLayout;
    ImageView userImage;
    TextView userName, title_mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        title_mainPage = findViewById(R.id.title_mainPage);
        title_mainPage.setText("Recommendation");

        updateUserInformation();
        //AuthorizationInterceptor.setRole("Merchant");
        updateMenuByrole(UserStaticInformation.getRole());
    }

    public void ClickMenu(View view){
        //Open drawer
        openDrawer(drawerLayout);
    }

    public void ClickUserIcon(View view){
        //Redirect
        redirectActivity(this, UserinfoActivity.class);
    }

    //meun list
    public void ClickRecommendation(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickFavorite(View view){
        //Redirect
        redirectActivity(this, FakeFavorite.class);
    }

    public void ClickShopNearBy(View view){
        //redirectActivity
        redirectActivity(this, SearchActivity.class);
    }

    public void ClickContactUs(View view){
        //redirectActivity
    }

    public void ClickSettingUser(View view){
        //redirectActivity
    }

    //logout
    public void ClickLogout(View view){
        //close app logout
        logout(this);
    }


    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START); //close only when the drawer is opened
    }

    public static void logout(Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aclass) {
        //initialize intent
        Intent intent = new Intent(activity, aclass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    public void updateUserInformation(){
        userName.setText(UserStaticInformation.getUserName());
        Picasso.get().load(UserStaticInformation.getUserImageUrl()).error(R.mipmap.ic_mobile_shopping_clothes_500_gray).into(userImage);
    }

    public void updateMenuByrole(String role){
        //user
        LinearLayout llRecommendation = findViewById(R.id.menu_recommendation_layout);
        LinearLayout llFavorite = findViewById(R.id.menu_favorite_layout);
        LinearLayout llShopNearBy = findViewById(R.id.menu_shopNearBy_layout);
        LinearLayout llContactUs = findViewById(R.id.menu_contactUs_layout);
        LinearLayout llSettingUser = findViewById(R.id.menu_setting_user_layout);
        //merchant
        LinearLayout llManageProduct = findViewById(R.id.menu_manageProduct_layout);
        LinearLayout llSettingMerchant = findViewById(R.id.menu_setting_merchant_layout);
        //admin
        LinearLayout llSettingAdmin = findViewById(R.id.menu_setting_admin_layout);
        if(role == "User"){
            llRecommendation.setVisibility(View.VISIBLE);
            llFavorite.setVisibility(View.VISIBLE);
            llShopNearBy.setVisibility(View.VISIBLE);
            llContactUs.setVisibility(View.VISIBLE);
            llSettingUser.setVisibility(View.VISIBLE);

            llManageProduct.setVisibility(View.GONE);
            llSettingMerchant.setVisibility(View.GONE);

            llSettingAdmin.setVisibility(View.GONE);

        }else if(role == "Merchant"){
            llRecommendation.setVisibility(View.GONE);
            llFavorite.setVisibility(View.GONE);
            llShopNearBy.setVisibility(View.GONE);
            llContactUs.setVisibility(View.GONE);
            llSettingUser.setVisibility(View.GONE);

            llManageProduct.setVisibility(View.VISIBLE);
            llSettingMerchant.setVisibility(View.VISIBLE);

            llSettingAdmin.setVisibility(View.GONE);
        }else if(role == "Admin"){
            llRecommendation.setVisibility(View.GONE);
            llFavorite.setVisibility(View.GONE);
            llShopNearBy.setVisibility(View.GONE);
            llContactUs.setVisibility(View.GONE);
            llSettingUser.setVisibility(View.GONE);

            llManageProduct.setVisibility(View.GONE);
            llSettingMerchant.setVisibility(View.GONE);

            llSettingAdmin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}