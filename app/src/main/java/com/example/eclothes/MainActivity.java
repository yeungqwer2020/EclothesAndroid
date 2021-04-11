package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;

import java.util.List;

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

        getMerchants();
    }

    private void getPosts() {
        Call<List<Product>> call = APIManager.getInstance().getAPIService().getProducts(null, null, null, null, null, null);

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