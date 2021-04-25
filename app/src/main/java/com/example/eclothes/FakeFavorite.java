package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.search.SearchActivity;
import com.squareup.picasso.Picasso;

public class FakeFavorite extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView userImage;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_favorite);

        drawerLayout = findViewById(R.id.drawer_layout);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);

        updateUserInformation();
    }

    public void ClickMenu(View view){
        //Open drawer
        MainPageActivity.openDrawer(drawerLayout);

    }

    public void ClickUserIcon(View view){
        MainPageActivity.closeDrawer(drawerLayout);
    }

    public void ClickRecommendation(View view){
        //Recreate activity
        MainPageActivity.redirectActivity(this,MainPageActivity.class);
    }

    //itself
    public void ClickFavorite(View view){
        //Redirect
        MainPageActivity.closeDrawer(drawerLayout);
    }

    public void ClickShopNearBy(View view){
        Log.i("ClickShopNearBy", "Search Clicked!");
        //redirectActivity
        MainPageActivity.redirectActivity(this, SearchActivity.class);
    }

    public void ClickLogout(View view){
        //close app logout
        MainPageActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainPageActivity.closeDrawer(drawerLayout);
    }

    public void updateUserInformation(){
        userName.setText(UserStaticInformation.getUserName());
        Picasso.get().load(UserStaticInformation.getUserImageUrl()).error(R.mipmap.ic_mobile_shopping_clothes_500_gray).into(userImage);
    }
}