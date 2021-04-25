package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.APIManager;
import com.example.eclothes.Config.Constant;
import com.example.eclothes.Models.Comment;
import com.example.eclothes.Models.Product;
import com.example.eclothes.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eclothes.Config.Constant.SERVER_URL;

public class ProductPage extends AppCompatActivity {

    TextView title, price, category, style, color, size, description, comment;
    final String USERID = UserStaticInformation.getUserId();
    String productID = "6072fe71d50d978995baa5a8";



    private String[] imageUrls = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Product");

        //Update123 starts
        productID = getIntent().getStringExtra("id");
        //Update123 ends

        title = findViewById(R.id.pp_title);
        price = findViewById(R.id.pp_price);
        category = findViewById(R.id.pp_category);
        style = findViewById(R.id.pp_style);
        color = findViewById(R.id.pp_color);
        size = findViewById(R.id.pp_size);
        description = findViewById(R.id.pp_Description);
        comment = findViewById(R.id.pp_comment);

        ViewPager viewPager = findViewById(R.id.pp_viewPager);
        ProductAdapter adapter  = new ProductAdapter(this, imageUrls);

        Call<Product> call = APIManager.getInstance().getAPIService().getProduct(productID);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()){
                    Log.d("Error code: " , String.valueOf(response.code()));
                    return;
                }

                Product product = response.body();
                //imageUrls = new String[product.getPhotos().size()];
                //for (int i = 0; i < product.getPhotos().size(); i++)
                //    imageUrls[i] = SERVER_URL + "/photo/merchants/" + product.getMerchant().get_id() + "/products/" + product.get_id() + "/" + product.getPhotos().get(i);
                if(product.getPhotos() != null && product.getPhotos().size() > 0) {
                    imageUrls[0] = SERVER_URL + "/photo/merchants/" + product.getMerchant().get_id() + "/products/" + product.get_id() + "/" + product.getPhotos().get(0);
                }
                //Log.d("Product Photo", imageUrls[0]);
                viewPager.setAdapter(adapter);

                title.setText(product.getName());
                price.setText("$" + product.getPrice());
                category.setText("Category: " + product.getCategory().getCategory());
                style.setText("Style: " + product.getStyle());

                StringBuilder colorS = new StringBuilder();
                colorS.append("Color: ");
                for (String color : product.getOptions().getColor())
                    colorS.append(color).append(" ");
                color.setText(colorS);

                StringBuilder sizeS = new StringBuilder();
                sizeS.append("Size: ");
                for (String size : product.getOptions().getSize())
                    sizeS.append(size).append(" ");
                size.setText(sizeS);

                description.setText("Description:\n" + product.getDesc());
                //comment
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        Call <List<com.example.eclothes.Models.Comment>> call2 = APIManager.getInstance().getAPIService().getComments(productID);

        call2.enqueue(new Callback<List<com.example.eclothes.Models.Comment>>() {
            @Override
            public void onResponse(Call<List<com.example.eclothes.Models.Comment>> call, Response<List<com.example.eclothes.Models.Comment>> response) {
                if(!response.isSuccessful()){
                    Log.d("Error code: " , String.valueOf(response.code()));
                    return;
                }

                List<com.example.eclothes.Models.Comment> comment = response.body();

                //Update123 starts

                //Update123 ends


            }

            @Override
            public void onFailure(Call<List<com.example.eclothes.Models.Comment>> call, Throwable t) {

            }
        });

        //update123

        comment.setOnClickListener(v -> {

            Intent intent = new Intent( getApplicationContext(), com.example.eclothes.alan.Comment.class);
            intent.putExtra("id", productID);
            startActivity(intent);

        });
        //update123
    }
}