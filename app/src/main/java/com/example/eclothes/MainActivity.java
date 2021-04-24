package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Models.Following;
import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;
import com.example.eclothes.Models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);


        removeFollowing();
    }

    private void removeFollowing() {
        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwN2M0NGExODZhZGFhZGNmZWM3MWY4NiIsImlhdCI6MTYxOTMwMDkyNCwiZXhwIjoxNjI3MDc2OTI0fQ.Isw6GzF9I6pI-xspWiW-Kq-1_Hyw9JffvcGvFN6iSng");

        Call<ResponseBody> call = APIManager.getInstance().getAPIService().removeFollowing("608497f00f8a4c1cfb16679c");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.code() == 200){
                    showMessage("Remove Follow Success");

                }
                try {
                    Log.d("Remove Follow", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Remove Follow", "failure");
            }
        });
    }

    private void addFollowing() {
        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwN2M0NGExODZhZGFhZGNmZWM3MWY4NiIsImlhdCI6MTYxOTMwMDkyNCwiZXhwIjoxNjI3MDc2OTI0fQ.Isw6GzF9I6pI-xspWiW-Kq-1_Hyw9JffvcGvFN6iSng");

        HashMap<String, String> map = new HashMap<>();
        map.put("merchant", "606b57c8a307473eb5d3f7f7");

        Call<Following> call = APIManager.getInstance().getAPIService().addFollowing(map);

        call.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> call, Response<Following> response) {
                if(response.isSuccessful() && response.code() == 200){
                    showMessage("Follow Success");

                    Following following = response.body();

                    String content = "";
                    content += following.getMerchant().get_id() + "\n";
                    content += following.getUser().get_id() + "\n";

                    showMessage(content);
                }
                try {
                    Log.d("Follow", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                Log.d("Follow", "failure");
            }
        });
    }

    private void update() {
        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwODE4ZjdmMGUwM2E0NDdkZTFlYjBjNCIsImlhdCI6MTYxOTI2NzA0MywiZXhwIjoxNjI3MDQzMDQzfQ.Dun9RBiSGaBTIdpgXPwYryfF8scxXOB3sCdcuD7rIAI");
        User updatedUserPart = new User();
        updatedUserPart.setFirstName("ggg");
        updatedUserPart.setLastName("abc");
        updatedUserPart.setUsername("helloworld");
        Call<User> call2 = APIManager.getInstance().getAPIService().updateMe(updatedUserPart);

        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.code() == 200){
                    showMessage("Update Success");

                    User user = response.body();

                    String content = "";
                    content += user.getFirstName() + "\n";
                    content += user.getLastName() + "\n";
                    content += user.getGender() + "\n";
                    content += user.getUsername() + "\n";

                    showMessage(content);
                }
                Log.d("Update", response.code() + "");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login", "failure");
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void getPosts() {
        Call<List<Product>> call = APIManager.getInstance().getAPIService().getProductsByMerchant(
                "606b57c8a307473eb5d3f7f7",
                null,
                null,
                null,
                null,
                null,
                null);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code" + response.code());
                    return;
                }

                List<Product> products = response.body();

                for (Product product : products) {
                    String content = "";
                    content += "ID: " + product.get_id() + "\n";
                    content += "Merchant: " + product.getMerchant().get_id() + "\n";
                    content += "Category: " + product.getCategory() + "\n";
                    content += "Desc: " + product.getDesc() + "\n";
                    content += "Name: " + product.getName() + "\n";
                    content += "Style: " + product.getStyle() + "\n";
                    content += "Price: " + product.getPrice() + "\n";
                    content += "Quantity: " + product.getQuantity() + "\n";
                    content += "Color: " + product.getOptions().getColor().toString() + "\n";
                    content += "Size: " + product.getOptions().getSize().toString() + "\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getMerchants() {
        Call<List<Merchant>> call = APIManager.getInstance().getAPIService().getMerchants(null);

        call.enqueue(new Callback<List<Merchant>>() {
            @Override
            public void onResponse(Call<List<Merchant>> call, Response<List<Merchant>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code" + response.code());
                    return;
                }

                List<Merchant> merchants = response.body();

                for (Merchant merchant : merchants) {
                    String content = "";
                    content += "ID: " + merchant.get_id() + "\n";
                    content += "Desc: " + merchant.getDesc() + "\n";
                    content += "Email: " + merchant.getEmail() + "\n";
                    content += "FirstName: " + merchant.getFirstName() + "\n";
                    content += "LastName: " + merchant.getLastName() + "\n";
                    content += "Phone: " + merchant.getPhone() + "\n";
                    content += "ShopName: " + merchant.getShopName() + "\n";

                    if (merchant.getLocation() != null)
                        content += "District: " + merchant.getLocation().getDistrict().getDistrict() + "\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Merchant>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}