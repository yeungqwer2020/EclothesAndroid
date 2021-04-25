package com.example.eclothes;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eclothes.alan.ProductPage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ShopmainActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;
    private TextView mTv1;
    ArrayList<EntityNews> entityNewsArrayList;
    private Button mBtnNew;
    private Button mBtnUp;
    private Button mBtnDown;
    private Button mBtnAboutus;
    private FloatingActionButton mBtnaddproduct;

    private CustomAdapter.RecyclerViewClickListener listener;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button cancel;
    private TextView shopname;
    private TextView address;
    private TextView district;
    private TextView phonenumber;
    private TextView openinghours;
    private ImageView image;
    private TextView email;
    ArrayList<shopinfo> Shopinfodetails;
    private boolean owner;

    private RequestQueue mQueue;
    private RequestQueue mQueueshopinfo;
    //ArrayList<String> photos = new ArrayList<String>();

    //String role;
    String merchantid;
    String usermerchant;
    String shopphoto;
    String photooo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopmain);

        recyclerview = (RecyclerView) findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new MyDecoration());

        entityNewsArrayList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(ShopmainActivity.this);
        mQueueshopinfo = Volley.newRequestQueue(ShopmainActivity.this);

        //get variables from other activity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //role = extras.getString("role");
            merchantid = extras.getString("merchantid");
            usermerchant = extras.getString("usermerchant");
        }

        prepare_news(0);
        popupshopinfo();



        mBtnaddproduct = (FloatingActionButton) findViewById(R.id.fab_1);
        mBtnaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopmainActivity.this, AddproductActivity.class);
                intent.putExtra("merchantid", merchantid);
                startActivity(intent);

            }
        });


        /* //check whether the login user is owner or not
        if (merchantid.compareTo(usermerchant) == 0)
            owner = true;
        else
            owner = false;*/
        owner = true;

        if (owner == true)
            mBtnaddproduct.setVisibility(View.VISIBLE);
        else if (owner == false)
            mBtnaddproduct.setVisibility(View.INVISIBLE);

        mBtnNew = (Button) findViewById(R.id.btn_2);
        mBtnNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBtnNew.setBackgroundColor(Color.parseColor("#FFFFCC"));
                mBtnUp.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBtnDown.setBackgroundColor(Color.parseColor("#FFFFFF"));
                prepare_news(0);
            }
        });

        mBtnUp = (Button) findViewById(R.id.btn_3);
        mBtnUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBtnUp.setBackgroundColor(Color.parseColor("#FFFFCC"));
                mBtnNew.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBtnDown.setBackgroundColor(Color.parseColor("#FFFFFF"));
                prepare_news(1);
            }
        });

        mBtnDown = (Button) findViewById(R.id.btn_4);
        mBtnDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBtnDown.setBackgroundColor(Color.parseColor("#FFFFCC"));
                mBtnUp.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBtnNew.setBackgroundColor(Color.parseColor("#FFFFFF"));
                prepare_news(2);
            }
        });

        mBtnAboutus = (Button) findViewById(R.id.btn_5);
        mBtnAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDialog();
            }
        });

        //String shopname = "XXX Company";
        mTv1 = (TextView) findViewById(R.id.tv_1);
        //mTv1.setText(shopname);
    }

    private void popupshopinfo() {
        //String shopid = merchantid;
        String shopid = "606b57c8a307473eb5d3f7f7";
        String shopinfourl = "http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/merchants/" + shopid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, shopinfourl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Shopinfodetails = new ArrayList<>();
                //Toast.makeText(ShopmainActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                //JSONArray jsonArray = response.getJSONArray("");
                JSONObject jsonObject = response;
                //for (int i = 0; i < jsonArray.length(); i++) {
                //  JSONObject jsonObject = jsonArray.getJSONObject(i);
                //JSONObject data = jsonArray.getJSONObject(i);
                String shopname;
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

                        /*String shopname = jsonObject.getString("shopName"); //shopName
                        String shopphoto = jsonObject.getString("photo"); //shopphoto
                        String username = jsonObject.getString("username"); //shopusername
                        String shopemail = jsonObject.getString("email"); //shopemail
                        String openinghours = jsonObject.getString("description"); //openinghours
                        String phonenumber = jsonObject.getString("phone"); //phonenumber
                        JSONObject location = jsonObject.getJSONObject("location");
                        String address = location.getString("address"); //address
                        JSONObject district = location.getJSONObject("district");
                        String districttt = district.getString("district"); //district*/
                //Toast.makeText(ShopmainActivity.this,"Response :" + shopname, Toast.LENGTH_LONG).show();
                mTv1.setText(shopname);

                String realuserphoto = "http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/users/" + shopid +"/" + shopphoto;

                Shopinfodetails.add(new shopinfo(realuserphoto,
                        shopname,
                        username,
                        address,
                        districttt,
                        phonenumber,
                        openinghours,
                        shopemail));

                //entityNewsArrayList.add(new EntityNews(photooo, productname, price, description));
                //}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueueshopinfo.add(request);
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }

    public void prepare_news(int a) {
        //String merchantid2 = merchantid;
        String merchantid2 = "60703e1aaa1c68484a568778";
        String url = "http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/merchants/" + merchantid2 + "/products?sort=createdAt";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //tvSelectedChoices.setText(response.toString());
                    //tvSelectedChoices.setText(response.toString());
                    //Toast.makeText(ShopmainActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                    //JSONArray jsonArray = response.getJSONArray("");
                    JSONArray jsonArray = response;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //JSONObject data = jsonArray.getJSONObject(i);
                        String productname = jsonObject.getString("name"); //productname
                        String photo = jsonObject.getString("photos");
                        String productid = jsonObject.getString("_id");
                        if (photo.compareTo("[]")!=0) {
                            photooo = "";
                            char[] firstphoto = new char[2000];
                            int count = 0;

                            for (int j = 2; j < 2000; j++) {
                                if (photo.charAt(j) != '"') {
                                    firstphoto[j - 2] = photo.charAt(j);
                                    count++;
                                } else
                                    break;
                            }
                            char[] photooo2 = new char[count];
                            for (int k = 0; k < count; k++) {
                                photooo2[k] = firstphoto[k];
                            }
                            photooo = new String(photooo2);
                        }
                        else
                            photooo = "[]";

                        //photooo is first photo string
                        String price = jsonObject.getString("price"); //price
                        String description = jsonObject.getString("description");

                        //Toast.makeText(ShopmainActivity.this,"Response :" + productid, Toast.LENGTH_LONG).show();


                        String realphotoo = "http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/merchants/" + merchantid2 +"/products/" + productid + "/" + photooo;
                        //photos.add(photooo);
                        //mBtnNew.append(photooo + " ");
                        //Toast.makeText(ShopmainActivity.this,"Response :" + productid, Toast.LENGTH_LONG).show();
                        if (photooo.compareTo("[]")==0)
                            entityNewsArrayList.add(new EntityNews(photooo, productname, price, description, productid));
                        else
                            entityNewsArrayList.add(new EntityNews(realphotoo, productname, price, description, productid));
                        /*entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 1", "2.2", "Description 1"));
                        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 2", "4.4", "Description 2"));
                        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 3", "3.3", "Description 3"));
                        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 4", "6.6", "Description 4"));
                        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 5", "5.5", "Description 5"));
                        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 6", "1.1", "Description 6"));*/

                    }
                    setOnClickListener();
                    CustomAdapter customAdapter = new CustomAdapter(entityNewsArrayList, ShopmainActivity.this, listener);
                    customAdapter.notifyDataSetChanged();
                    recyclerview.setAdapter(customAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //shopname.setText("Fail");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        /*entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 1", "2.2", "Description 1"));
        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 2", "4.4", "Description 2"));
        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 3", "3.3", "Description 3"));
        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 4", "6.6", "Description 4"));
        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 5", "5.5", "Description 5"));
        entityNewsArrayList.add(new EntityNews("https://i.ibb.co/x88xTKP/image.png", "Product 6", "1.1", "Description 6"));*/
        if (a == 1)
            Collections.sort(entityNewsArrayList, EntityNews.PriceUp);
        else if (a == 2)
            Collections.sort(entityNewsArrayList, EntityNews.PriceDown);
        //CustomAdapter customAdapter = new CustomAdapter(entityNewsArrayList, ShopmainActivity.this);
        //recyclerview.setAdapter(customAdapter);
        mQueue.add(request);
        entityNewsArrayList.clear();
    }

    private void setOnClickListener() {
        listener = new CustomAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ProductPage.class); //send variable to other activity
                intent.putExtra("id", entityNewsArrayList.get(position).getProductid()); //send productid to ProductpageActivity
                startActivity(intent);
            }
        };
    }

    public void createNewDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View PopupView = getLayoutInflater().inflate(R.layout.popup, null);
        shopname = (TextView) PopupView.findViewById(R.id.tv_4);
        address = (TextView) PopupView.findViewById(R.id.tv_6);
        district = (TextView) PopupView.findViewById(R.id.tv_8);
        phonenumber = (TextView) PopupView.findViewById(R.id.tv_10);
        openinghours = (TextView) PopupView.findViewById(R.id.tv_14);
        image = (ImageView) PopupView.findViewById(R.id.iv_1);
        email = (TextView) PopupView.findViewById(R.id.tv_12);

        cancel = (Button) PopupView.findViewById(R.id.btn_1);
        dialogBuilder.setView(PopupView);
        dialog = dialogBuilder.create();
        dialog.show();




        //Shopinfodetails.add(new shopinfo("https://i.ibb.co/x88xTKP/image.png", "Shop Shop", "user123", "Park Avenue Tower Causeway Bay, Wan Chai District, Hong Kong", "Wan Chai123", "24740777", "Mon-Fri: 00:00 - 23:00\nSat: 00:00 - 22:59\nSun: Close", "XXX@company.com"));

        if (shopphoto.compareTo("fail to access") == 0)
            Picasso.get().load(R.drawable.profilepic).into(image);
        else
            Picasso.get().load(Shopinfodetails.get(0).image).into(image);
        //Picasso.get().load(Shopinfodetails.get(0).image).into(image);
        shopname.setText(Shopinfodetails.get(0).shopname);
        address.setText(Shopinfodetails.get(0).address);
        district.setText(Shopinfodetails.get(0).district);
        phonenumber.setText(Shopinfodetails.get(0).phonenumber);
        openinghours.setText(Shopinfodetails.get(0).openinghours);
        email.setText(Shopinfodetails.get(0).email);

        address.setMovementMethod(new ScrollingMovementMethod());
        openinghours.setMovementMethod(new ScrollingMovementMethod());
        email.setMovementMethod(new ScrollingMovementMethod());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}