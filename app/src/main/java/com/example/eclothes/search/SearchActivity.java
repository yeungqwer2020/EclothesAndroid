package com.example.eclothes.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.FakeFavorite;
import com.example.eclothes.MainPageActivity;
import com.example.eclothes.R;
import com.example.eclothes.APIManager;
import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Models.Category;
import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbarWidget;
    View filter, map;
    TabLayout searchTabLayout;
    List<Merchant> merchantList;
    List<Product> productList;

    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    MerchantRecyclerViewAdapter merchantRecyclerViewAdapter;
    ProductRecyclerViewAdapter productRecyclerViewAdapter;

    String keyword, merchant_keyword, product_keyword, sort, category, style, district_id;
    Double minPrice, maxPrice;
    private int tab_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        drawerLayout = findViewById(R.id.drawer_layout);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //toolbarWidget = (Toolbar) findViewById(R.id.searchAppBar);

        filter = (View) findViewById(R.id.filter);
        map = (View) findViewById(R.id.map);

        merchantList = new ArrayList<>();
        productList = new ArrayList<>();

        searchTabLayout = (TabLayout) findViewById(R.id.searchTabLayout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            keyword = bundle.getString("keyword");
            sort = !bundle.getString("sort").isEmpty() ? bundle.getString("sort") : null;
            category = !bundle.getString("category").isEmpty() ? bundle.getString("category") : null;
            style = !bundle.getString("style").isEmpty() ? bundle.getString("style") : null;
            minPrice = bundle.getString("minPrice").isEmpty() || bundle.getString("minPrice") == null ? null : Double.parseDouble(bundle.getString("minPrice"));
            maxPrice = bundle.getString("maxPrice").isEmpty() || bundle.getString("maxPrice") == null ? null : Double.parseDouble(bundle.getString("maxPrice"));
            district_id = !bundle.getString("district_id").isEmpty() ? bundle.getString("district_id") : null;
        }

        merchant_keyword = keyword;
        product_keyword = keyword;

        getExistingMerchant(merchant_keyword, district_id);
        getExistingProduct(product_keyword, sort, category, style, minPrice, maxPrice);

        searchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_index = tab.getPosition();
                if (tab.getPosition() == 0) {
                    Log.i("tab.getPosition() == 0", "Tab:" + tab);
                    updateRecyclerView(merchantList, 0);
                } else if (tab.getPosition() == 1) {
                    Log.i("tab.getPosition() == 1", "Tab:" + tab);
                    updateRecyclerView(productList, 1);
                } else {
                    Log.i("Other tab", "Tab:" + tab);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // navigationIcon onClick
        /*.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Search toolbarWidget", "navigationIcon Clicked!");
                        endActivity();
                    }
                }
        );*/

        // Filter onClick
        if(filter != null) {
            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("onClick - filter", "Filter Clicked!");
                    launchSearchFilterActivity(searchTabLayout.getSelectedTabPosition());
                }
            });
        }

        // Map onClick
        if(map != null) {
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("onClick - map", "Map Clicked!");
                    launchMapSearchingActivity();
                }
            });
        }

        recyclerView = (RecyclerView)findViewById(R.id.searchRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        merchantRecyclerViewAdapter = new MerchantRecyclerViewAdapter(SearchActivity.this, merchantList, "60703a98e334f646971a1dc5"); //userId hard code
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(merchantRecyclerViewAdapter);

        Log.i("SearchActivity","SearchActivity Created.");
    }


    public void ClickMenu(View view){
        //Open drawer
        Log.i("ClickMenu", "Click!");
        MainPageActivity.openDrawer(drawerLayout);

    }

    public void ClickUserIcon(View view){
        MainPageActivity.closeDrawer(drawerLayout);
    }

    public void ClickRecommendation(View view){
        //Recreate activity
        MainPageActivity.redirectActivity(this,MainPageActivity.class);
    }

    public void ClickFavorite(View view){
        //Redirect
        MainPageActivity.redirectActivity(this, FakeFavorite.class);
    }

    //itself
    public void ClickShopNearBy(View view){
        //redirectActivity
        MainPageActivity.closeDrawer(drawerLayout);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.i("onActivityResult", "requestCode == 1");
            if (resultCode == RESULT_OK) {
                Log.i("onActivityResult", "resultCode == RESULT_OK");
                int tabIndex = data.getIntExtra("tabIndex", -1);
                keyword = data.getStringExtra("keyword");
                sort = data.getStringExtra("sort") == null || data.getStringExtra("sort").isEmpty() ? null : data.getStringExtra("sort");
                category = data.getStringExtra("category") == null || data.getStringExtra("category").isEmpty() ? null : data.getStringExtra("category");
                style = data.getStringExtra("style") == null || data.getStringExtra("style").isEmpty() ? null : data.getStringExtra("style");
                minPrice = data.getStringExtra("minPrice") == null || data.getStringExtra("minPrice").isEmpty()  ? null : Double.parseDouble(data.getStringExtra("minPrice"));
                maxPrice = data.getStringExtra("maxPrice") == null || data.getStringExtra("maxPrice").isEmpty() ? null : Double.parseDouble(data.getStringExtra("maxPrice"));
                district_id = data.getStringExtra("district_id") == null || data.getStringExtra("district_id").isEmpty() ? null : data.getStringExtra("district_id");
                Log.i("onActivityResult", "onActivityResult - tabIndex :" + tabIndex);
                Log.i("onActivityResult", "onActivityResult - Shop :" + keyword + ", " + district_id);
                Log.i("onActivityResult", "onActivityResult - Product :" + keyword + ", " + sort + ", " + category + ", " + style + ", " + minPrice + ", " + maxPrice);

                if (tabIndex == 0) {
                    merchant_keyword = keyword;
                    getExistingMerchant(merchant_keyword, district_id);
                } else if (tabIndex == 1) {
                    product_keyword = keyword;
                    getExistingProduct(product_keyword, sort, category, style, minPrice, maxPrice);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                Log.i("onActivityResult", "resultCode == RESULT_CANCELED");
                Toast.makeText(SearchActivity.this, "Fail to get result", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateRecyclerView(List dataList, int tabIndex) {
        recyclerView = (RecyclerView)findViewById(R.id.searchRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        if (tabIndex == 0) {
            merchantRecyclerViewAdapter = new MerchantRecyclerViewAdapter(SearchActivity.this, dataList, "60703a98e334f646971a1dc5"); //userId hard code
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(merchantRecyclerViewAdapter);
        } else if (tabIndex == 1) {
            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(dataList, "60703a98e334f646971a1dc5"); //userId hard code
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(productRecyclerViewAdapter);
        }
    }

    private void getExistingMerchant(String keyword, String district_id) {
        Call<List<Merchant>> call = APIManager.getInstance().getAPIService().getMerchants(keyword, district_id);

        call.enqueue(new Callback<List<Merchant>>() {
            @Override
            public void onResponse(Call<List<Merchant>> call, Response<List<Merchant>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                merchantList = new ArrayList<>();
                merchantList = response.body();

                if (tab_index == 0 && merchantList.size() > 0) {
                    Log.i("getExistingMerchant", "Updating");
                    updateRecyclerView(merchantList, 0);
                }
            }

            @Override
            public void onFailure(Call<List<Merchant>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExistingProduct(String keyword, String sort, String category, String style, Double minPrice, Double maxPrice) {
        Call<List<Product>> call = APIManager.getInstance().getAPIService().getProducts(keyword, sort, category, style, minPrice, maxPrice);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                productList = new ArrayList<>();
                productList = response.body();

                if (tab_index == 1 && productList.size() > 0) {
                    Log.i("getExistingProduct", "Updating");
                    updateRecyclerView(productList, 1);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchSearchFilterActivity(int tabIndex) {
        Log.i("SearchFilterActivity", "tabIndex = " + tabIndex);
        Intent searchFilterIntent = new Intent(this, SearchFilterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("key", tabIndex);
        searchFilterIntent.putExtras(bundle);

        startActivityForResult(searchFilterIntent, 1);
    }

    private void launchMapSearchingActivity() {
        Log.i("MapSearchingActivity", "Map logo clicked!");
        Intent searchFilterIntent = new Intent(this, MapSearchingActivity.class);
        startActivity(searchFilterIntent);
    }

    private void endActivity() {
        Log.i("endActivity", "Search Activity Finish!");
        finish();
    }
}