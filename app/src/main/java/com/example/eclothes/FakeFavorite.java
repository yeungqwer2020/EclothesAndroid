package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.eclothes.search.SearchActivity;

public class FakeFavorite extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_favorite);

        drawerLayout = findViewById(R.id.drawer_layout);
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
}