package com.example.eclothes.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.R;
import com.example.eclothes.APIManager;
import com.example.eclothes.Models.Category;
import com.example.eclothes.Models.District;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFilterActivity extends AppCompatActivity {

    int value = -1;
    TextInputLayout district, category, style_layout, sort;
    TextInputEditText keyword, style, minPrice, maxPrice;
    TextView priceRangeLabel;
    GridLayout priceRangeLayout;
    Button search;
    Toolbar toolbarWidget;
    AutoCompleteTextView selected_district, selected_category, selected_sort;

    LinearLayout.LayoutParams removeMargin;

    String[] district_option, sort_option;
    List<String> category_option;
    HashMap<String, String> district_id, category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        district_option = new String[] {"Islands","Kwai Tsing","North","Sai Kung","Sha Tin","Tai Po","Tsuen Wan",
                                        "Tuen Mun","Yuen Long","Kowloon City","Kwun Tong","Sham Shui Po",
                                        "Wong Tai Sin","Yau Tsim Mong","Central and Western","Eastern",
                                       "Southern","Wan Chai"};

        district_id = new HashMap<String, String>();

        category_option = new ArrayList<String>();

        category_id = new HashMap<String, String>();

        sort_option = new String[] {"price low to high", "price high to low"};

        keyword = (TextInputEditText) findViewById(R.id.keyword_text);
        
        // Shops Search Options
        district = (TextInputLayout) findViewById(R.id.district_selection);
        selected_district = (AutoCompleteTextView) findViewById(R.id.district_option);

        // Products Search Options
        category = (TextInputLayout) findViewById(R.id.category_selection);
        selected_category = (AutoCompleteTextView) findViewById(R.id.category_option);
        style = (TextInputEditText) findViewById(R.id.style_text);
        style_layout = (TextInputLayout) findViewById(R.id.style_layout);
        priceRangeLabel = (TextView) findViewById(R.id.priceRange_label);
        priceRangeLayout = (GridLayout) findViewById(R.id.priceRange_layout);
        minPrice = (TextInputEditText) findViewById(R.id.priceRange_min_text);
        maxPrice = (TextInputEditText) findViewById(R.id.priceRange_max_text);
        sort = (TextInputLayout) findViewById(R.id.sort_selection) ;
        selected_sort = (AutoCompleteTextView) findViewById(R.id.sort_option);

        toolbarWidget = (Toolbar) findViewById(R.id.filterAppBar);
        search = (Button) findViewById(R.id.search_button);

        removeMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        removeMargin.setMargins(0, 0, 0, 0);

        try {
            //URL categoryUrl = new URL("http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/categories");
            //JSONObject jo = (JSONObject) new JSONTokener(IOUtils.toString(categoryUrl.openStream())).nextValue();

            //Log.i("categoryJson get", "json: " + jo.getString("data"));

            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
                value = bundle.getInt("key");

            if (value == -1) {
                throw new Exception("Somethings Wrong!");
            }

            if (value == 0) {
                category.removeAllViewsInLayout();
                category.setLayoutParams(removeMargin);
                style_layout.removeAllViewsInLayout();
                style_layout.setLayoutParams(removeMargin);
                priceRangeLabel.setTextSize(0);
                priceRangeLabel.setLayoutParams(removeMargin);
                priceRangeLayout.removeAllViewsInLayout();
                priceRangeLayout.setLayoutParams(removeMargin);
                sort.removeAllViewsInLayout();
                sort.setLayoutParams(removeMargin);

                Log.i("onCreate", "before getExistingDistrict");
                getExistingDistrict();
                Log.i("onCreate", "after getExistingDistrict");

                ArrayAdapter<String> district_adapter =
                        new ArrayAdapter<>(
                                this,
                                R.layout.dropdown_list_item,
                                district_option);

                AutoCompleteTextView editTextFilledExposedDropdown_district = findViewById(R.id.district_option);
                editTextFilledExposedDropdown_district.setAdapter(district_adapter);

            } else if (value == 1) {
                district.removeAllViewsInLayout();
                district.setLayoutParams(removeMargin);

                Log.i("onCreate", "before getExistingCategory");
                getExistingCategory();
                Log.i("onCreate", "after getExistingCategory");

                for (String category : category_option) {
                    Log.i("onCreate", "category: " + category);
                }

                ArrayAdapter<String> sort_adapter =
                        new ArrayAdapter<>(
                                this,
                                R.layout.dropdown_list_item,
                                sort_option);

                AutoCompleteTextView editTextFilledExposedDropdown_sort = findViewById(R.id.sort_option);
                editTextFilledExposedDropdown_sort.setAdapter(sort_adapter);
            }

            // navigationIcon onClick
            toolbarWidget.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("Filters toolbarWidget", "navigationIcon Clicked!");
                            endActivity();
                        }
                    }

            );

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("onClick - search", "Search Clicked! :");

                    String keyword_input = "";
                    String district_input = "";
                    String category_input = "";
                    String style_input = "";
                    String minPrice_input = "";
                    String maxPrice_input = "";
                    String sort_input = "";

                    keyword_input = keyword.getText().toString();

                    Intent searchResultIntent = new Intent();
                    searchResultIntent.putExtra("tabIndex", value);
                    searchResultIntent.putExtra("keyword", keyword_input);
                    
                    if (value == 0) {
                        district_input = selected_district.getText().toString();

                        if (!district_input.equals("")) {
                            searchResultIntent.putExtra("district_id", district_id.get(district_input));
                        }
                        Log.i("search.Listener", "search.setOnClickListener - Shop :" + keyword_input + ", " + district_input);
                    } else if (value ==1) {
                        category_input = selected_category.getText().toString();
                        style_input = style.getText().toString();
                        minPrice_input = minPrice.getText().toString();
                        maxPrice_input = maxPrice.getText().toString();
                        sort_input = selected_sort.getText().toString();

                        if (sort_input.equals("price low to high")) {
                            Log.i("search.Listener", "search.setOnClickListener - sort_input : price");
                            searchResultIntent.putExtra("sort", "price");
                        } else if (sort_input.equals("price high to low")) {
                            Log.i("search.Listener", "search.setOnClickListener - sort_input : -price");
                            searchResultIntent.putExtra("sort", "-price");
                        }

                        if (!category_input.equals("")) {
                            searchResultIntent.putExtra("category", category_id.get(category_input));
                        }

                        if (!style_input.equals("")) {
                            searchResultIntent.putExtra("style", style_input);
                        }

                        if (!minPrice_input.equals("")) {
                            searchResultIntent.putExtra("minPrice", minPrice_input);
                        }

                        if (!maxPrice_input.equals("")) {
                            searchResultIntent.putExtra("maxPrice", maxPrice_input);
                        }
                        Log.i("search.Listener", "search.setOnClickListener - Product :" + keyword_input + ", " + sort_input + ", " + category_input + ", " + style_input + ", " + minPrice_input + ", " + maxPrice_input);
                    }

                    Log.i("onClick - search", "searchResultIntent: " + value + ", " + keyword_input);

                    setResult(RESULT_OK, searchResultIntent);
                    endActivity();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            endActivity();
        }
    }

    private void getExistingCategory() {
        Call<List<Category>> call = APIManager.getInstance().getAPIService().getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                List<Category> categoryDataList = response.body();

                for (Category category : categoryDataList) {
                    category_option.add(category.getCategory());
                    Log.i("categoryDataList", category.getCategory());

                    category_id.put(category.getCategory(), category.get_id());
                }

                ArrayAdapter<String> category_adapter =
                        new ArrayAdapter<>(
                                SearchFilterActivity.this,
                                R.layout.dropdown_list_item,
                                category_option);

                AutoCompleteTextView editTextFilledExposedDropdown_category = findViewById(R.id.category_option);
                editTextFilledExposedDropdown_category.setAdapter(category_adapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(SearchFilterActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExistingDistrict() {
        Call<List<District>> call = APIManager.getInstance().getAPIService().getDistricts();

        call.enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                List<District> districts = response.body();

                for (District district : districts) {
                    district_id.put( district.getDistrict(), district.get_id());
                    Log.i("districts", district.get_id() + ", " + district.getDistrict());
                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                Toast.makeText(SearchFilterActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void endActivity() {
        Log.i("endActivity", "Filter Activity Finish!");
        finish();
    }
}