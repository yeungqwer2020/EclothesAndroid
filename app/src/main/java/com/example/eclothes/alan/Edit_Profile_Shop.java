package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.APIManager;
import com.example.eclothes.Models.District;
import com.example.eclothes.Models.Location;
import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;
import com.example.eclothes.Models.Region;
import com.example.eclothes.Models.User;
import com.example.eclothes.R;
import com.example.eclothes.UserinfoActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.parseColor;
import static com.example.eclothes.APIManager.*;

public class Edit_Profile_Shop extends AppCompatActivity {

    int radioID;
    private static final String TAG = "MyActivity";
    private static final String MERCHANTID = UserStaticInformation.getUserId();
    // get text and update to server
    // EditText et;
    TextView district;
    ImageView profilepic;
    Button changeP, changeD, save;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText username, shopName, address, phoneNum, openingHr;
    //
    List<Double> getCoordinate;
    Region getRegion;

    HashMap<String, String> districtMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__shop);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Edit Profile");

        district = findViewById(R.id.edit_district_shop);
        profilepic = findViewById(R.id.edit_profile_shop);
        changeP = findViewById(R.id.edit_change_shop);
        changeD = findViewById(R.id.district_change);
        save = findViewById(R.id.edit_save_shop);

        username = findViewById(R.id.editET_username_shop);
        shopName = findViewById(R.id.editET_shopname_shop);
        address = findViewById(R.id.editET_address_shop);
        phoneNum = findViewById(R.id.editET_phonenum_shop);
        openingHr = findViewById(R.id.editET_openinghr_shop);

        radioGroup = findViewById(R.id.edit_district_radioGroup);
        radioButton = findViewById(radioID);

        radioGroup.setVisibility(View.GONE);

        changeD.setOnClickListener(v -> {
            radioGroup.setVisibility(View.VISIBLE);
        });

        putDistrict();


        //get json & set text

        Call<Merchant> call = APIManager.getInstance().getAPIService().getMerchant(MERCHANTID);

        call.enqueue(new Callback<Merchant>() {
            @Override
            public void onResponse(Call<Merchant> call, Response<Merchant> response) {
                if (!response.isSuccessful()) {
                    Log.d("Update", response.code() + "");
                    Toast.makeText(Edit_Profile_Shop.this, "Response is unsuccessful when retrieving favourite list. + ", Toast.LENGTH_LONG).show();
                    return;
                }

                Merchant merchant = response.body();
                // set profile pic
                username.setText(merchant.getUsername());
                shopName.setText(merchant.getShopName());
                address.setText(merchant.getLocation().getAddress());
                phoneNum.setText(merchant.getPhone());
                openingHr.setText(merchant.getDesc()); // opening hour is missing in json

                getCoordinate = merchant.getLocation().getCoordinates();


            }

            @Override
            public void onFailure(Call<Merchant> call, Throwable t) {
                Toast.makeText(Edit_Profile_Shop.this, "Response is fail when retrieving favourite list.", Toast.LENGTH_LONG).show();
            }
        });

        save.setOnClickListener(v -> {
            Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();
            // update to the server
            Merchant updatedMerchantPart = new Merchant();

            //updatedUserPart.setFirstName("I am a cat");
            updatedMerchantPart.setUsername(username.getText().toString().trim());
            updatedMerchantPart.setShopName(shopName.getText().toString().trim());
            //updatedMerchantPart.setLocation(new Location(getCoordinate, address.getText().toString().trim(), new District(districtMap.get(radioButton.getText()), radioButton.getText())));
            updatedMerchantPart.setPhone(phoneNum.getText().toString().trim());
            updatedMerchantPart.setDesc(openingHr.getText().toString().trim());

            Call<Merchant> call2 = APIManager.getInstance().getAPIService().updateMe(updatedMerchantPart);

            call2.enqueue(new Callback<Merchant>() {
                @Override
                public void onResponse(Call<Merchant> call, Response<Merchant> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        //showMessage("Update Success");

                        Intent intent = new Intent(Edit_Profile_Shop.this, UserinfoActivity.class);
                        startActivity(intent);

//                        User user = response.body();
//
//                        String content = "";
//                        content += user.getFirstName() + "\n";
//                        content += user.getLastName() + "\n";
//                        content += user.getGender() + "\n";
//                        content += user.getUsername() + "\n";

                        //showMessage(content);

                        //username.setText();
                    }
                    Log.d("Update", response.code() + "");
                }

                @Override
                public void onFailure(Call<Merchant> call, Throwable t) {
                    Log.d("Login", "failure");
                }
            });

            //Call<District> call3 = APIManager
        });
    }

    public void checkButton2(View v){
        radioID = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioID);
        radioGroup.setVisibility(View.GONE);
        district.setText("District: " + radioButton.getText());


        // can be deleted
        Toast.makeText(this, "Selected District: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void putDistrict(){
        districtMap.put("Islands", "606b53b2a307473eb5d3f7d1");
        districtMap.put("Kwai Tsing", "606b53b2a307473eb5d3f7d2");
        districtMap.put("North", "606b53b2a307473eb5d3f7d3");
        districtMap.put("Sai Kung", "606b53b2a307473eb5d3f7d4");
        districtMap.put("Sha Tin", "606b53b2a307473eb5d3f7d5");
        districtMap.put("Tai Po", "606b53b2a307473eb5d3f7d6");
        districtMap.put("Tsuen Wan", "606b53b2a307473eb5d3f7d7");
        districtMap.put("Tuen Mun", "606b53b2a307473eb5d3f7d8");
        districtMap.put("Yuen Long", "606b53b2a307473eb5d3f7d9");
        districtMap.put("Kowloon City", "606b53b2a307473eb5d3f7da");
        districtMap.put("Kwun Tong", "606b53b2a307473eb5d3f7db");
        districtMap.put("Sham Shui Po", "606b53b2a307473eb5d3f7dc");
        districtMap.put("Wong Tai Sin", "606b53b2a307473eb5d3f7dd");
        districtMap.put("Yau Tsim Mong", "606b53b2a307473eb5d3f7de");
        districtMap.put("Hong Kong Island", "606b53b2a307473eb5d3f7df");
        districtMap.put("Eastern", "606b53b2a307473eb5d3f7e0");
        districtMap.put("Southern", "606b53b2a307473eb5d3f7e1");
        districtMap.put("Wan Chai", "606b53b2a307473eb5d3f7e2");
    }
}