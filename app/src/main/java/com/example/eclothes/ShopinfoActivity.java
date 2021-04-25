package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eclothes.alan.Edit_Profile_Shop;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopinfoActivity extends AppCompatActivity {

    private Button mBtnEditshopinfo;
    private TextView tvusername;
    private TextView tvshopname;
    private TextView tvaddress;
    private TextView tvdistrict;
    private TextView tvphonenumber;
    private TextView tvopeninghours;
    private ImageView image;
    private TextView tvemail;

    ArrayList<shopinfo> Shopinfodetails;

    private RequestQueue mQueue;

    String merchantid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopinfo);

        mBtnEditshopinfo = (Button) findViewById(R.id.btn_1);

        tvusername = (TextView) findViewById(R.id.tv_2);
        tvshopname = (TextView) findViewById(R.id.tv_4);
        tvaddress = (TextView) findViewById(R.id.tv_6);
        tvdistrict = (TextView) findViewById(R.id.tv_8);
        tvphonenumber = (TextView) findViewById(R.id.tv_10);
        tvopeninghours = (TextView) findViewById(R.id.tv_14);
        image = (ImageView) findViewById(R.id.iv_1);
        tvemail = (TextView) findViewById(R.id.tv_12);

        Shopinfodetails = new ArrayList<>();

        mQueue = Volley.newRequestQueue(ShopinfoActivity.this);
        showshopinfo();

        //Shopinfodetails.add(new shopinfo("https://i.ibb.co/x88xTKP/image.png", "Shop Shop", "user123", "Park Avenue Tower Causeway Bay, Wan Chai District, Hong Kong", "Wan Chai123", "24740777", "Mon-Fri: 00:00 - 23:00\nSat: 00:00 - 22:59\nSun: Close", "ASD@company.com"));

        /*Picasso.get().load(Shopinfodetails.get(0).image).into(image);
        tvusername.setText(Shopinfodetails.get(0).username);
        tvshopname.setText(Shopinfodetails.get(0).shopname);
        tvaddress.setText(Shopinfodetails.get(0).address);
        tvdistrict.setText(Shopinfodetails.get(0).district);
        tvphonenumber.setText(Shopinfodetails.get(0).phonenumber);
        tvopeninghours.setText(Shopinfodetails.get(0).openinghours);
        tvemail.setText(Shopinfodetails.get(0).email);*/

        tvaddress.setMovementMethod(new ScrollingMovementMethod());
        tvopeninghours.setMovementMethod(new ScrollingMovementMethod());
        tvemail.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras(); //get merchantid
        if (extras != null){
            //role = extras.getString("role");
            merchantid = extras.getString("merchantid");
        }


        mBtnEditshopinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopinfoActivity.this, Edit_Profile_Shop.class);
                startActivity(intent);
            }
        });

    }

    private void showshopinfo() {
        //String shopid = merchantid;
        String shopid = "6070668934ec3a5b70a726f4";
        String shopinfourl = "http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/merchants/" + shopid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, shopinfourl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Shopinfodetails = new ArrayList<>();
                //Toast.makeText(ShopinfoActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                //JSONArray jsonArray = response.getJSONArray("");
                JSONObject jsonObject = response;
                //for (int i = 0; i < jsonArray.length(); i++) {
                //  JSONObject jsonObject = jsonArray.getJSONObject(i);
                //JSONObject data = jsonArray.getJSONObject(i);
                String shopname;
                String shopphoto;
                String username;
                String shopemail;
                String openinghours;
                String phonenumber;
                String address;
                String districttt;

                try {
                    shopname = jsonObject.getString("shopName");
                }
                catch (JSONException e){
                    shopname = "fail to access";
                }
                try {
                    shopphoto = jsonObject.getString("photo");
                }
                catch (JSONException e){
                    shopphoto = "fail to access";
                }
                try {
                    username = jsonObject.getString("username");
                }
                catch (JSONException e){
                    username = "fail to access";
                }
                try {
                    shopemail = jsonObject.getString("email");
                }
                catch (JSONException e){
                    shopemail = "fail to access";
                }
                try {
                    openinghours = jsonObject.getString("description");
                }
                catch (JSONException e){
                    openinghours = "fail to access";
                }
                try {
                    phonenumber = jsonObject.getString("phone");
                }
                catch (JSONException e){
                    phonenumber = "fail to access";
                }
                try {
                    JSONObject location = jsonObject.getJSONObject("location");
                    address = location.getString("address");
                    JSONObject district = location.getJSONObject("district");
                    districttt = district.getString("district");
                }
                catch (JSONException e){
                    address = "fail to access";
                    districttt = "fail to access";
                }
                //String shopname = jsonObject.getString("shopName"); //shopName
                //String shopphoto = jsonObject.getString("photo"); //shopphoto
                //String username = jsonObject.getString("username"); //shopusername
                //String shopemail = jsonObject.getString("email"); //shopemail
                //String openinghours = jsonObject.getString("description"); //openinghours
                //String phonenumber = jsonObject.getString("phone"); //phonenumber
                //JSONObject location = jsonObject.getJSONObject("location");
                //String address = location.getString("address"); //address
                //JSONObject district = location.getJSONObject("district");
                //String districttt = district.getString("district"); //district
                //Toast.makeText(ShopinfoActivity.this,"Response :" + district.getString("district"), Toast.LENGTH_LONG).show();

                String realshopphoto = "http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/merchants/" + shopid +"/" + shopphoto;

                Shopinfodetails.add(new shopinfo(realshopphoto,
                        shopname,
                        username,
                        address,
                        districttt,
                        phonenumber,
                        openinghours,
                        shopemail));
                if (shopphoto.compareTo("fail to access") == 0)
                    Picasso.get().load(R.drawable.profilepic).into(image);
                else
                    Picasso.get().load(Shopinfodetails.get(0).image).into(image);
                tvusername.setText(Shopinfodetails.get(0).username);
                tvshopname.setText(Shopinfodetails.get(0).shopname);
                tvaddress.setText(Shopinfodetails.get(0).address);
                tvdistrict.setText(Shopinfodetails.get(0).district);
                tvphonenumber.setText(Shopinfodetails.get(0).phonenumber);
                tvopeninghours.setText(Shopinfodetails.get(0).openinghours);
                tvemail.setText(Shopinfodetails.get(0).email);

                //entityNewsArrayList.add(new EntityNews(photooo, productname, price, description));
                //}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}