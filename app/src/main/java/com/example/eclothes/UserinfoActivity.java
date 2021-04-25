package com.example.eclothes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.Models.Favorite;
import com.example.eclothes.alan.Edit_Profile_User;
import com.example.eclothes.alan.Favourite;
import com.example.eclothes.search.SearchActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserinfoActivity extends AppCompatActivity {
    private Button mBtnEdituserinfo;
    private TextView tvusername;
    private TextView tvfirstname;
    private TextView tvlastname;
    private TextView tvsex;
    private TextView tvphonenumber;
    private ImageView image;
    private TextView tvemail;
    DrawerLayout drawerLayout;
    ImageView userImage;
    TextView userName, title_mainPage;

    ArrayList<userinfo> Userinfodetails;

    private RequestQueue mQueue;

    String userid = UserStaticInformation.getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        drawerLayout = findViewById(R.id.drawer_layout);
        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        title_mainPage = findViewById(R.id.title_mainPage);
        title_mainPage.setText("Userinfo");

        updateUserInformation();

        tvusername = (TextView) findViewById(R.id.tv_2);
        tvfirstname = (TextView) findViewById(R.id.tv_4);
        tvlastname = (TextView) findViewById(R.id.tv_6);
        tvsex = (TextView) findViewById(R.id.tv_8);
        tvphonenumber = (TextView) findViewById(R.id.tv_10);
        image = (ImageView) findViewById(R.id.iv_1);
        tvemail = (TextView) findViewById(R.id.tv_12);

        Userinfodetails = new ArrayList<>();
        /*
        Userinfodetails.add(new userinfo("https://i.ibb.co/x88xTKP/image.png", "fuck", "you", "suck", "12345678", "test@gmail.com"));

        Picasso.get().load(Userinfodetails.get(0).image).into(image);
        tvusername.setText(Userinfodetails.get(0).username);
        tvfirstname.setText(Userinfodetails.get(0).firstname);
        tvlastname.setText(Userinfodetails.get(0).lastname);
        //tvsex.setText(Userinfodetails.get(0).sex);
        tvphonenumber.setText(Userinfodetails.get(0).phonenumber);
        tvemail.setText(Userinfodetails.get(0).email);
        */
        //Userinfodetails = new ArrayList<>();

        Bundle extras = getIntent().getExtras(); //get userid
        if (extras != null){
            //role = extras.getString("role");
        }

        mQueue = Volley.newRequestQueue(UserinfoActivity.this);
        showuserinfo();

        tvemail.setMovementMethod(new ScrollingMovementMethod());

        mBtnEdituserinfo = (Button) findViewById(R.id.btn_1);
        mBtnEdituserinfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserinfoActivity.this, Edit_Profile_User.class);
                startActivity(intent);
            }
        });
    }

    public void ClickMenu(View view){
        //Open drawer
        MainPageActivity.openDrawer(drawerLayout);

    }

    public void ClickUserIcon(View view){
        MainPageActivity.closeDrawer(drawerLayout);
    }

    /*public void ClickRecommendation(View view){
        //Recreate activity
        MainPageActivity.redirectActivity(this,MainPageActivity.class);
    }*/

    //itself
    public void ClickFavorite(View view){
        //Redirect
        MainPageActivity.redirectActivity(this, Favourite.class);
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

    private void showuserinfo() {
        String userid2 = UserStaticInformation.getUserId(); // set userid
        //String userid2 = "607394adb88a82667573ea56";
        String userinfourl = "http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/users/" + userid2;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, userinfourl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Userinfodetails = new ArrayList<>();
                //Toast.makeText(ShopinfoActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
                //JSONArray jsonArray = response.getJSONArray("");
                JSONObject jsonObject = response;
                //for (int i = 0; i < jsonArray.length(); i++) {
                //  JSONObject jsonObject = jsonArray.getJSONObject(i);
                //JSONObject data = jsonArray.getJSONObject(i);
                String gender;
                String userphoto;
                String username;
                String userfirstname;
                String userlastname;
                String useremail;
                String phonenumber;
                try {
                    userphoto = jsonObject.getString("photo");
                }
                catch (JSONException e){
                    userphoto = "fail to access";
                }
                try {
                    username = jsonObject.getString("username");
                }
                catch (JSONException e){
                    username = "fail to access";
                }
                try {
                    userfirstname = jsonObject.getString("firstName");
                }
                catch (JSONException e){
                    userfirstname = "fail to access";
                }
                try {
                    userlastname = jsonObject.getString("lastName");
                }
                catch (JSONException e){
                    userlastname = "fail to access";
                }
                try {
                    useremail = jsonObject.getString("email");
                }
                catch (JSONException e){
                    useremail = "fail to access";
                }
                try {
                    phonenumber = jsonObject.getString("phone");
                }
                catch (JSONException e){
                    phonenumber = "fail to access";
                }
                //String userphoto = jsonObject.getString("photo"); //userphoto
                //String username = jsonObject.getString("username"); //username
                //String userfirstname = jsonObject.getString("firstName");//userfirstname
                //String userlastname = jsonObject.getString("lastName");//userlastname
                //String useremail = jsonObject.getString("email"); //useremail
                //String phonenumber = jsonObject.getString("phone"); //userphonenumber

                try {
                    gender = jsonObject.getString("gender");
                }
                catch (JSONException e){
                    gender = "fail to access";
                }

                //Toast.makeText(ShopinfoActivity.this,"Response :" + district.getString("district"), Toast.LENGTH_LONG).show();

                String realuserphoto = "http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/users/" + userid2 +"/" + userphoto;
                //Toast.makeText(UserinfoActivity.this,"Response :" + realuserphoto, Toast.LENGTH_LONG).show();

                Userinfodetails.add(new userinfo(realuserphoto,
                        userfirstname,
                        userlastname,
                        username,
                        phonenumber,
                        gender,
                        useremail));
                if (userphoto.compareTo("fail to access") == 0)
                    Picasso.get().load(R.drawable.profilepic).into(image);
                else
                    Picasso.get().load(Userinfodetails.get(0).image).into(image);

                //Picasso.get().load(www.google.com/image/1).placeholder(context.getResources().getDrawable(R.drawable.default_person_image)).error(context.getResources().getDrawable(R.drawable.default_person_image)).into(pictureView);

                tvusername.setText(Userinfodetails.get(0).username);
                tvfirstname.setText(Userinfodetails.get(0).firstname);
                tvlastname.setText(Userinfodetails.get(0).lastname);
                tvsex.setText(Userinfodetails.get(0).sex);
                tvphonenumber.setText(Userinfodetails.get(0).phonenumber);
                tvemail.setText(Userinfodetails.get(0).email);

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