package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.APIManager;
import com.example.eclothes.Models.User;
import com.example.eclothes.R;
import com.example.eclothes.UserinfoActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile_User extends AppCompatActivity {

    int radioID;
    ImageView profilepic;
    RadioGroup radioGroup;
    RadioButton radioButton;
    // get text and update to server
    EditText username, firstname, lastname, phone;
    Button change, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__user);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Edit Profile");

        profilepic = findViewById(R.id.edit_profile_user);
        change = findViewById(R.id.edit_change_user);
        username = findViewById(R.id.editET_username_user);
        firstname = findViewById(R.id.editET_firstname_shop);
        lastname = findViewById(R.id.editET_lastname_shop);
        phone = findViewById(R.id.editET_phonenum_user);

        radioGroup = findViewById(R.id.edit_sex_radioGroup);
        radioButton = findViewById(radioID);
        save = findViewById(R.id.edit_save_user);
        save.setOnClickListener(v -> {
            Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();
            int radioID = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioID);

            User updatedUserPart = new User();

            //updatedUserPart.setFirstName("I am a cat");
            updatedUserPart.setFirstName(firstname.getText().toString().trim());
            updatedUserPart.setLastName(lastname.getText().toString().trim());
            updatedUserPart.setUsername(username.getText().toString().trim());
            updatedUserPart.setPhone(phone.getText().toString().trim());
            updatedUserPart.setGender(radioButton.getText().toString().trim());

            Call<User> call2 = APIManager.getInstance().getAPIService().updateMe(updatedUserPart);

            call2.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        //showMessage("Update Success");

                        Intent intent = new Intent(Edit_Profile_User.this, UserinfoActivity.class);
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
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Login", "failure");
                }
            });
        });

        final String ID = UserStaticInformation.getUserId();
        Call<User> call3 = APIManager.getInstance().getAPIService().getUser(ID);

        call3.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.code() == 200){
                    //showMessage("Update Success");

//
//
//                        String content = "";
//                        content += user.getFirstName() + "\n";
//                        content += user.getLastName() + "\n";
//                        content += user.getGender() + "\n";
//                        content += user.getUsername() + "\n";

                    //showMessage(content);

                    User user = response.body();
                    username.setText(user.getUsername());
                    firstname.setText(user.getFirstName());
                    lastname.setText(user.getLastName());
                    phone.setText(user.getPhone());

                    if(user.getGender().equals("male"))
                        radioGroup.check(R.id.radioButton1);
                    else
                        radioGroup.check(R.id.radioButton2);

                }
                Log.d("Update", response.code() + "");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login", "failure");
            }
        });

    }

    public void checkButton(View v){
        radioID = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioID);

        // can be deleted
        Toast.makeText(this, "Selected Sex: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}