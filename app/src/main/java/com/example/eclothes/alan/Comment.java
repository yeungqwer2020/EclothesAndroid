package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.APIManager;
import com.example.eclothes.Models.Product;
import com.example.eclothes.Models.User;
import com.example.eclothes.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eclothes.Config.Constant.SERVER_URL;

public class Comment extends AppCompatActivity {

    ImageView productPic;
    TextView title, price;
    EditText question;
    Button ask;
    static String PRODUCTID = "6072fe71d50d978995baa5a8";
    Product getProduct;
    User getUser;
    String getComment;
    String getUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Comment");

        productPic = findViewById(R.id.comment_productpic_user);
        title = findViewById(R.id.comment_title);
        price = findViewById(R.id.comment_price);
        question = findViewById(R.id.commentET_question);
        ask = findViewById(R.id.comment_ask);

        final String USERID = UserStaticInformation.getUserId();
        PRODUCTID = getIntent().getStringExtra("id");

        // get product & set text
        Call<Product> currentProduct = APIManager.getInstance().getAPIService().getProduct(PRODUCTID);
        currentProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()){
                    Log.d("Error code: " , String.valueOf(response.code()));
                    return;
                }

                getProduct = response.body();
                String url = SERVER_URL + "/photo/merchants/" + getProduct.getMerchant().get_id() + "/products/" + getProduct.get_id() + "/" + getProduct.getPhotos().get(0);
                Picasso.get().load(url).into(productPic);
                title.setText(getProduct.getName());
                price.setText("$" + getProduct.getPrice());

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("Login", "failure");
            }
        });

        // get user
        Call<User> currentUser = APIManager.getInstance().getAPIService().getUser(USERID);
        currentUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.d("Error code: " , String.valueOf(response.code()));
                    return;
                }
                getUser = response.body();
                getUserID = getUser.get_id();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login", "failure");
            }
        });

        ask.setOnClickListener(v -> {
            getComment = question.getText().toString().trim();
            com.example.eclothes.Models.Comment comment = new com.example.eclothes.Models.Comment(getProduct, getComment, getUserID);

            Call<com.example.eclothes.Models.Comment> call = APIManager.getInstance().getAPIService().createComment(comment);
            call.enqueue(new Callback<com.example.eclothes.Models.Comment>() {
                @Override
                public void onResponse(Call<com.example.eclothes.Models.Comment> call, Response<com.example.eclothes.Models.Comment> response) {
                    if(!response.isSuccessful()){
                        Log.d("Error code: " , String.valueOf(response.code()));
                        return;
                    }

                }

                @Override
                public void onFailure(Call<com.example.eclothes.Models.Comment> call, Throwable t) {

                }
            });

            //update123
            Toast.makeText(Comment.this, "Your comment has been sent.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent( getApplicationContext(), com.example.eclothes.alan.ProductPage.class);
            intent.putExtra("id", PRODUCTID);
            startActivity(intent);
            //update123
        });


    }
}