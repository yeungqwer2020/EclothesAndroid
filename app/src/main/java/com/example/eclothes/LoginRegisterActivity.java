package com.example.eclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.API.UserStaticInformation;
import com.example.eclothes.Models.CurrentMerchant;
import com.example.eclothes.Models.CurrentUser;
import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;
import com.example.eclothes.Models.User;
import com.example.eclothes.search.SearchActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eclothes.MainPageActivity.redirectActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    private TextInputLayout menuRoleLayout, menuGenderLayout;
    private AutoCompleteTextView menuRole, menuGender;

    private TextInputLayout edtUsernameLayout, edtEmailLayout, edtPasswordLayout;
    private TextView txtSwitchLoginRegister;

    private Button btnLoginRegister, btnRegisterBack;

    private ConstraintLayout loginRegisterConstraintLayout;
    private TextView txtTitle;
    private TextInputLayout edtFirstNameLayout, edtLastNameLayout, edtPhoneLayout, edtShopNameLayout;

    private ProgressBar progressBar;

    private final String btn_tag_login = "btn_tag_login";
    private final String btn_tag_register = "btn_tag_register";
    private final String btn_tag_next = "btn_tag_next";

    private final String txt_switch_tag_login = "txt_switch_tag_login";
    private final String txt_switch_tag_register = "txt_switch_tag_register";

    String[] roles;
    ArrayAdapter<String> menuRoleAdapter;

    String[] genders;
    ArrayAdapter<String> menuGenderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        menuRoleLayout = findViewById(R.id.menu_role_layout);
        menuRole = findViewById(R.id.menu_role);

        menuGenderLayout = findViewById(R.id.menu_gender_layout);
        menuGender = findViewById(R.id.menu_gender);

        roles = getResources().getStringArray(R.array.roles);
        genders = getResources().getStringArray(R.array.genders);

        menuRoleAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_row_item, roles);
        menuRole.setAdapter(menuRoleAdapter);

        menuGenderAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_row_item, genders);
        menuGender.setAdapter(menuGenderAdapter);

        edtUsernameLayout = findViewById(R.id.edt_username_layout);
        edtEmailLayout = findViewById(R.id.edt_email_layout);
        edtPasswordLayout = findViewById(R.id.edt_password_layout);
        txtSwitchLoginRegister = findViewById(R.id.txt_switch_login_register);
        btnLoginRegister = findViewById(R.id.btn_login_register);
        btnRegisterBack = findViewById(R.id.btn_register_back);

        loginRegisterConstraintLayout = findViewById(R.id.login_register_layout);
        txtTitle = findViewById(R.id.txt_title);

        // User & Merchant
        edtFirstNameLayout = findViewById(R.id.edt_register_first_name_layout);
        edtLastNameLayout = findViewById(R.id.edt_register_last_name_layout);
        edtPhoneLayout = findViewById(R.id.edt_register_phone_layout);

        // Merchant
        edtShopNameLayout = findViewById(R.id.edt_register_shop_name_layout);


        progressBar = findViewById(R.id.progress_bar);

        btnLoginRegister.setOnClickListener(loginRegisterOnClick);
        btnRegisterBack.setOnClickListener(registerBackOnClick);
        txtSwitchLoginRegister.setOnClickListener(switchLoginRegisterOnClick);

    }


    public void rootLayoutTapped(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int convertDpToPx(int dp) {
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    private void processLoginRegister(final boolean isProcess) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isProcess) {

                    btnLoginRegister.setVisibility(View.INVISIBLE);

                    progressBar.setVisibility(View.VISIBLE);
                } else {

                    btnLoginRegister.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


    private View.OnClickListener switchLoginRegisterOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (txtSwitchLoginRegister.getTag().toString()) {
                case txt_switch_tag_register:
                    clear();
                    showRegisterFields();

                    // change button to Next
                    btnLoginRegister.setText(R.string.next_button_text);
                    btnLoginRegister.setTag(btn_tag_next);

                    // change switch text to switch to login
                    txtSwitchLoginRegister.setText(R.string.switch_login_text);
                    txtSwitchLoginRegister.setTag(txt_switch_tag_login);

                    break;
                case txt_switch_tag_login:
                    clear();
                    showLoginFields();

                    setLoginRegisterButtonBelowPassword();

                    // change button to Login
                    btnLoginRegister.setText(R.string.login_button_text);
                    btnLoginRegister.setTag(btn_tag_login);

                    // change switch text to switch to register
                    txtSwitchLoginRegister.setText(R.string.switch_register_text);
                    txtSwitchLoginRegister.setTag(txt_switch_tag_register);

                    break;
            }

        }
    };

    private View.OnClickListener loginRegisterOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (btnLoginRegister.getTag().toString()) {
                case btn_tag_next:
                    if (edtEmailLayout.getEditText().getText().toString().trim().equals("") ||
                            edtPasswordLayout.getEditText().getText().toString().trim().equals("")) {
                        Toast.makeText(LoginRegisterActivity.this, "Username, Email and Password are required!", Toast.LENGTH_SHORT).show();
                    } else {

                        showRequiredRegisterNextFields();

                        btnLoginRegister.setText(R.string.register_button_text);
                        btnLoginRegister.setTag(btn_tag_register);

                        if (menuRole.getText().toString().equals("User")) {
                            menuGenderLayout.setVisibility(View.VISIBLE);

                            setLoginRegisterButtonBelowGender();
                        } else if (menuRole.getText().toString().equals("Merchant")){
                            edtShopNameLayout.setVisibility(View.VISIBLE);

                            setLoginRegisterButtonBelowShopName();
                        }

                    }
                    break;
                case btn_tag_register:
                    Log.d("register", "success");

                    if (edtFirstNameLayout.getEditText().getText().toString().trim().equals("") ||
                            edtLastNameLayout.getEditText().getText().toString().trim().equals("") ||
                            edtPhoneLayout.getEditText().getText().toString().trim().equals("") ||
                            menuRole.getText().toString().equals("User") && menuGender.getText().toString().trim().equals("") ||
                            menuRole.getText().toString().equals("Merchant") && edtShopNameLayout.getEditText().getText().toString().trim().equals("")
                            ) {

                        String missingFields = "Missing fields:";
                        if (menuRole.getText().toString().equals("User") && menuGender.getText().toString().trim().equals("")) {
                            missingFields += "Gender\n";
                        }
                        if (menuRole.getText().toString().equals("Merchant") && edtShopNameLayout.getEditText().getText().toString().trim().equals("")) {
                            missingFields += "Shop Name\n";
                        }
                        if (edtFirstNameLayout.getEditText().getText().toString().trim().equals("")) {
                            missingFields += "First Name\n";
                        }
                        if (edtLastNameLayout.getEditText().getText().toString().trim().equals("")) {
                            missingFields += "Last Name\n";
                        }
                        if (edtPhoneLayout.getEditText().getText().toString().trim().equals("")) {
                            missingFields += "Phone Number\n";
                        }

                        Toast.makeText(LoginRegisterActivity.this, missingFields, Toast.LENGTH_SHORT).show();
                    } else {

                        processLoginRegister(true);

                        register();

                        processLoginRegister(false);

                    }
                    break;
                case btn_tag_login:

                    login();
                    break;
            }

        }
    };

    private View.OnClickListener registerBackOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showRegisterFields();

            txtSwitchLoginRegister.setVisibility(View.VISIBLE);
            btnRegisterBack.setVisibility(View.GONE);

            btnLoginRegister.setText(R.string.next_button_text);
            btnLoginRegister.setTag(btn_tag_next);

            setLoginRegisterButtonBelowPassword();
        }
    };

    private void register() {
        String email = edtEmailLayout.getEditText().getText().toString().trim().toLowerCase();
        String username = edtUsernameLayout.getEditText().getText().toString().trim();
        String password = edtPasswordLayout.getEditText().getText().toString().trim();
        // TODO: make another edit text
        String passwordConfirm = edtPasswordLayout.getEditText().getText().toString().trim();
        String firstName = edtFirstNameLayout.getEditText().getText().toString().trim();
        String lastName = edtLastNameLayout.getEditText().getText().toString().trim();
        String phone = edtPhoneLayout.getEditText().getText().toString().trim();

        if (menuRole.getText().toString().trim().equals("User")) {
            String gender = menuGender.getText().toString().toLowerCase();

            User newUser = new User(firstName, lastName, phone, email, password, passwordConfirm, gender, username);
            Toast.makeText(LoginRegisterActivity.this, "User: " + newUser.getGender(), Toast.LENGTH_SHORT).show();

            Log.d("register", newUser.getEmail());
            Call<CurrentUser> call = APIManager.getInstance().getAPIService().register(newUser);


            call.enqueue(new Callback<CurrentUser>() {
                @Override
                public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                    if(response.isSuccessful() && response.code() == 201){
                        showMessage("Register Success");

                        CurrentUser currentUser = response.body();

                        String content = "";
                        content += currentUser.getToken() + "\n";
                        content += currentUser.getUser().getFirstName() + "\n";
                        content += currentUser.getUser().getLastName() + "\n";
                        content += currentUser.getUser().getGender() + "\n";
                        content += currentUser.getUser().getUsername() + "\n";

                        showMessage(content);
                        restartActivity();
                    }
                    Log.d("register", response.code() + "");
                }

                @Override
                public void onFailure(Call<CurrentUser> call, Throwable t) {
                    Log.d("register", "fail");
                }
            });


        } else if (menuRole.getText().toString().trim().equals("Merchant")) {
            String shopName = edtShopNameLayout.getEditText().getText().toString().trim();

            Merchant newMerchant = new Merchant(firstName, lastName, shopName, phone, email, password, passwordConfirm, username);
            Call<CurrentMerchant> call = APIManager.getInstance().getAPIService().register(newMerchant);

            call.enqueue(new Callback<CurrentMerchant>() {
                @Override
                public void onResponse(Call<CurrentMerchant> call, Response<CurrentMerchant> response) {
                    if(response.isSuccessful() && response.code() == 201){
                        showMessage("Register Success");

                        CurrentMerchant currentMerchant = response.body();

                        String content = "";
                        content += currentMerchant.getToken() + "\n";
                        content += currentMerchant.getMerchant().getFirstName() + "\n";
                        content += currentMerchant.getMerchant().getLastName() + "\n";
                        content += currentMerchant.getMerchant().getShopName() + "\n";
                        content += currentMerchant.getMerchant().getUsername() + "\n";

                        showMessage(content);
                        restartActivity();
                    }
                    Log.d("register", response.code() + "");
                    Log.d("register", response.errorBody() + "");
                }

                @Override
                public void onFailure(Call<CurrentMerchant> call, Throwable t) {
                    Log.d("register", "fail");
                }
            });
        }

    }

    private void update() {
        User updatedUserPart = new User();
        updatedUserPart.setFirstName("I am a cat");
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

    private void login() {
        String username = edtUsernameLayout.getEditText().getText().toString().trim();
        String password = edtPasswordLayout.getEditText().getText().toString().trim();

        if (menuRole.getText().toString().trim().equals("User")) {

            User user = new User(username, password);

            CurrentUser currentUser;

            Call<CurrentUser> call = APIManager.getInstance().getAPIService().login(user);


            call.enqueue(new Callback<CurrentUser>() {
                @Override
                public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        showMessage("Login Success");

                        CurrentUser currentUser = response.body();

                        String content = "";
                        content += currentUser.getToken() + "\n";
                        content += currentUser.getUser().getFirstName() + "\n";
                        content += currentUser.getUser().getLastName() + "\n";
                        content += currentUser.getUser().getGender() + "\n";
                        content += currentUser.getUser().getUsername() + "\n";

                        AuthorizationInterceptor.setToken(currentUser.getToken());
                        showMessage(content);

                        UserStaticInformation.setUserId(currentUser.getUser().get_id());
                        UserStaticInformation.setUserName(currentUser.getUser().getUsername());
                        UserStaticInformation.setRole("User");
                        UserStaticInformation.setUserImageUrl(currentUser.getUser().getPhoto());

                        update();
                        promoteToMainPage();


                    }
                    Log.d("Login", response.code() + "");
                }

                @Override
                public void onFailure(Call<CurrentUser> call, Throwable t) {
                    Log.d("Login", "failure");
                }
            });



        } else if (menuRole.getText().toString().trim().equals("Merchant")) {


            Merchant merchant = new Merchant(username, password);
            Call<CurrentMerchant> call = APIManager.getInstance().getAPIService().login(merchant);

            call.enqueue(new Callback<CurrentMerchant>() {
                @Override
                public void onResponse(Call<CurrentMerchant> call, Response<CurrentMerchant> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        showMessage("Login Success");

                        CurrentMerchant currentMerchant = response.body();

                        String content = "";
                        content += currentMerchant.getToken() + "\n";
                        content += currentMerchant.getMerchant().getFirstName() + "\n";
                        content += currentMerchant.getMerchant().getLastName() + "\n";
                        content += currentMerchant.getMerchant().getShopName() + "\n";
                        content += currentMerchant.getMerchant().getUsername() + "\n";

                        showMessage(content);
                        //intent to MainPage
                        AuthorizationInterceptor.setToken(currentMerchant.getToken());
                        promoteToMainPage();

                        UserStaticInformation.setUserId(currentMerchant.getMerchant().get_id());
                        UserStaticInformation.setUserName(currentMerchant.getMerchant().getUsername());
                        UserStaticInformation.setRole("Merchant");
                        UserStaticInformation.setUserImageUrl(currentMerchant.getMerchant().getPhoto());
                    }

                }

                @Override
                public void onFailure(Call<CurrentMerchant> call, Throwable t) {
                    Log.d("Login", "failure");
                }
            });
        }

    }

    private void promoteToMainPage(){
        MainPageActivity.redirectActivity(this, SearchActivity.class);
    }

    private void hideAllFields() {
        menuRoleLayout.setVisibility(View.GONE);
        edtUsernameLayout.setVisibility(View.GONE);
        edtPasswordLayout.setVisibility(View.GONE);
        edtEmailLayout.setVisibility(View.GONE);
        edtFirstNameLayout.setVisibility(View.GONE);
        edtLastNameLayout.setVisibility(View.GONE);
        edtPhoneLayout.setVisibility(View.GONE);
        menuGenderLayout.setVisibility(View.GONE);
        edtShopNameLayout.setVisibility(View.GONE);
    }

    /*
     * SHOW LOGIN FIELDS
     */
    private void showLoginFields() {
        hideAllFields();

        menuRoleLayout.setVisibility(View.VISIBLE);
        edtUsernameLayout.setVisibility(View.VISIBLE);
        edtPasswordLayout.setVisibility(View.VISIBLE);
    }

    /*
    * SHOW REGISTER FIELDS
    */
    private void showRegisterFields() {
        hideAllFields();

        menuRoleLayout.setVisibility(View.VISIBLE);
        edtUsernameLayout.setVisibility(View.VISIBLE);
        edtPasswordLayout.setVisibility(View.VISIBLE);
        edtEmailLayout.setVisibility(View.VISIBLE);
    }

    private void showRequiredRegisterNextFields() {
        hideAllFields();

        edtFirstNameLayout.setVisibility(View.VISIBLE);
        edtLastNameLayout.setVisibility(View.VISIBLE);
        edtPhoneLayout.setVisibility(View.VISIBLE);

        btnRegisterBack.setVisibility(View.VISIBLE);
        txtSwitchLoginRegister.setVisibility(View.GONE);
    }

    /*
    * CLEAR FIELDS
    * */
    private void clear() {
        edtUsernameLayout.getEditText().getText().clear();
        edtEmailLayout.getEditText().getText().clear();
        edtPasswordLayout.getEditText().getText().clear();
        edtFirstNameLayout.getEditText().getText().clear();
        edtLastNameLayout.getEditText().getText().clear();
        edtPhoneLayout.getEditText().getText().clear();
        edtShopNameLayout.getEditText().getText().clear();
    }

    /*
     * SET LOGIN REGISTER BUTTON BELOW PASSWORD FIELD
     * */
    private void setLoginRegisterButtonBelowPassword() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginRegisterConstraintLayout);
        constraintSet.connect(R.id.btn_login_register, ConstraintSet.TOP, R.id.edt_password_layout, ConstraintSet.BOTTOM, convertDpToPx(16));
        constraintSet.applyTo(loginRegisterConstraintLayout);
    }

    /*
     *  USER REGISTER
     *  SET LOGIN REGISTER BUTTON BELOW GENDER FIELD
     * */
    private void setLoginRegisterButtonBelowGender() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginRegisterConstraintLayout);
        constraintSet.connect(R.id.btn_login_register, ConstraintSet.TOP, R.id.menu_gender_layout, ConstraintSet.BOTTOM, convertDpToPx(16));
        constraintSet.applyTo(loginRegisterConstraintLayout);
    }

    /*
     *  MERCHANT REGISTER
     *  SET LOGIN REGISTER BUTTON BELOW SHOP NAME FIELD
     * */
    private void setLoginRegisterButtonBelowShopName() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginRegisterConstraintLayout);;
        constraintSet.connect(R.id.btn_login_register, ConstraintSet.TOP, R.id.edt_register_shop_name_layout, ConstraintSet.BOTTOM, convertDpToPx(16));
        constraintSet.applyTo(loginRegisterConstraintLayout);
    }

    private void restartActivity() {
        Log.d("login", "success");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void showMessage(String message) {
        //Toast.makeText(LoginRegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}