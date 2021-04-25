package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.APIManager;
import com.example.eclothes.MainPageActivity;
import com.example.eclothes.Models.Favorite;
import com.example.eclothes.Models.Following;
import com.example.eclothes.R;
import com.example.eclothes.UserinfoActivity;
import com.example.eclothes.search.SearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.parseColor;
import static com.example.eclothes.Config.Constant.SERVER_URL;

public class Favourite extends AppCompatActivity {

    Button product, shop;
    final String USER_ID = UserStaticInformation.getUserId();

    DrawerLayout drawerLayout;
    ImageView userImage;
    TextView userName, title_mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Favourite");

        drawerLayout = findViewById(R.id.drawer_layout);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        title_mainPage = findViewById(R.id.title_mainPage);
        title_mainPage.setText("Favorite");

        updateUserInformation();

        //Product
        ListView listviewProduct = findViewById(R.id.listviewProduct);
        //ImageView imageviewP = findViewById(R.id.imageView2);
        ArrayList<FavouriteProduct> fpList = new ArrayList<>();

        //Shop
        ListView listviewShop = findViewById(R.id.listviewShop);
        listviewShop.setVisibility(View.INVISIBLE);
        //ImageView imageviewS = findViewById(R.id.imageView2);
        ArrayList<FavouriteShop> fsList = new ArrayList<>();

//        FavouriteProduct fp1 = new FavouriteProduct(imageviewP, "title1", 1);
//        FavouriteProduct fp2 = new FavouriteProduct(imageviewP, "title2", 2);
//        FavouriteProduct fp3 = new FavouriteProduct(imageviewP, "title3", 3);
//        FavouriteProduct fp4 = new FavouriteProduct(imageviewP, "title4", 4);
//        FavouriteProduct fp5 = new FavouriteProduct(imageviewP, "title5", 5);
//        FavouriteProduct fp6 = new FavouriteProduct(imageviewP, "title6", 6);
//        FavouriteProduct fp7 = new FavouriteProduct(imageviewP, "title7", 7);
//        FavouriteProduct fp8 = new FavouriteProduct(imageviewP, "title8", 8);
//        FavouriteProduct fp9 = new FavouriteProduct(imageviewP, "title9", 9);
//        FavouriteProduct fp10 = new FavouriteProduct(imageviewP, "title10", 10);
//
//        fpList.add(fp1);fpList.add(fp2);fpList.add(fp3);fpList.add(fp4);fpList.add(fp5);
//        fpList.add(fp6);fpList.add(fp7);fpList.add(fp8);fpList.add(fp9);fpList.add(fp10);

        Call<List<Favorite>> call = APIManager.getInstance().getAPIService().getFavorites(USER_ID);
        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Favourite.this, "Error response code:" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<Favorite> favorites = response.body();
                for (Favorite favorite : favorites) {
                    // given string how to assign it into an image view named imageviewP
                    // /photo/merchants/{{merchantId}}/products/{{productId}}/{{filename.jpeg}}
                    String url = SERVER_URL + "/photo/merchants/" + favorite.getProduct().getMerchant().get_id() + "/products/" + favorite.getProduct().get_id() + "/" + favorite.getProduct().getPhotos().get(0);
                    Log.d("PhotoFav", url);

                    FavouriteProduct fp = new FavouriteProduct(url, favorite.getProduct().getName(), favorite.getProduct().getPrice());
                    fpList.add(fp);
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Toast.makeText(Favourite.this, "Response is fail when retrieving favourite list.", Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Following>> call2 = APIManager.getInstance().getAPIService().getFollowings(USER_ID);
        call2.enqueue(new Callback<List<Following>>() {
            @Override
            public void onResponse(Call<List<Following>> call, Response<List<Following>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Favourite.this, "Error response code:" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<Following> followings = response.body();
                for (Following following : followings) {
                    // given string how to assign it into an image view named imageviewS
                    String url = SERVER_URL + "/photo/merchants/" + following.getMerchant().get_id() + "/" + following.getMerchant().getPhoto();
                    Log.d("PhotoFollowing", url);

                    FavouriteShop fs = new FavouriteShop(url, following.getMerchant().getUsername(), following.getMerchant().getLocation().getAddress());
                    fsList.add(fs);
                }
            }

            @Override
            public void onFailure(Call<List<Following>> call, Throwable t) {
                Toast.makeText(Favourite.this, "Response is fail when retrieving favourite list.", Toast.LENGTH_LONG).show();
            }
        });

        CustomAdapter caProduct = new CustomAdapter(this, R.layout.adapter_view_layout, fpList);
        caProduct.notifyDataSetChanged();
        listviewProduct.setAdapter(caProduct);

        //////////////////////////////////////


//        FavouriteShop fs1 = new FavouriteShop(imageviewS, "shopName1", "address1");
//        FavouriteShop fs2 = new FavouriteShop(imageviewS, "shopName2", "address2");
//        FavouriteShop fs3 = new FavouriteShop(imageviewS, "shopName3", "address3");
//        FavouriteShop fs4 = new FavouriteShop(imageviewS, "shopName4", "address4");
//        FavouriteShop fs5 = new FavouriteShop(imageviewS, "shopName5", "address5");
//        FavouriteShop fs6 = new FavouriteShop(imageviewS, "shopName6", "address6");
//        FavouriteShop fs7 = new FavouriteShop(imageviewS, "shopName7", "address7");
//        FavouriteShop fs8 = new FavouriteShop(imageviewS, "shopName8", "address8");
//        FavouriteShop fs9 = new FavouriteShop(imageviewS, "shopName9", "address9");
//
//        fsList.add(fs1);fsList.add(fs2);fsList.add(fs3);fsList.add(fs4);fsList.add(fs5);
//        fsList.add(fs6);fsList.add(fs7);fsList.add(fs8);fsList.add(fs9);

        CustomAdapterShop caShop = new CustomAdapterShop(this, R.layout.adapter_view_layout, fsList);
        caShop.notifyDataSetChanged();
        listviewShop.setAdapter(caShop);

        product = findViewById(R.id.product);
        shop = findViewById(R.id.shop);

        product.setOnClickListener(v -> {
            listviewShop.setVisibility(View.GONE);
            listviewProduct.setVisibility(View.VISIBLE);
            product.setBackgroundColor(Color.WHITE);
            shop.setBackgroundColor(parseColor("#00BCD4"));
        });

        shop.setOnClickListener(v -> {
            listviewProduct.setVisibility(View.GONE);
            listviewShop.setVisibility(View.VISIBLE);
            shop.setBackgroundColor(Color.WHITE);
            product.setBackgroundColor(parseColor("#00BCD4"));
        });
    }

    public void ClickMenu(View view){
        //Open drawer
        MainPageActivity.openDrawer(drawerLayout);

    }

    public void ClickUserIcon(View view){
        //Redirect
        MainPageActivity.redirectActivity(this, UserinfoActivity.class);
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