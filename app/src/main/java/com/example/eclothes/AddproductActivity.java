
package com.example.eclothes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Models.Category;
import com.example.eclothes.Models.Options;
import com.example.eclothes.Models.Product;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AddproductActivity extends AppCompatActivity {
    Button addimage;
    Button mCategory;
    TextView tvSelectedChoices;
    ImageView ivimage;
    EditText etStyle;
    EditText etname;
    EditText etprice;
    EditText etdescription;
    EditText etcolor;
    EditText etsize;
    private List<String> list;
    Button mConfirm;
    private RequestQueue mQueue;
    ArrayList<Category> categories = new ArrayList<>();

    String merchantid;

    private Uri photoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);

        addimage = (Button) findViewById(R.id.btn_1);
        mCategory = (Button) findViewById(R.id.btn_2);
        mConfirm = (Button) findViewById(R.id.btn_4);

        ivimage = (ImageView) findViewById(R.id.product_image);
        tvSelectedChoices = (TextView) findViewById(R.id.category);
        etStyle = (EditText) findViewById(R.id.style);
        etname = (EditText) findViewById(R.id.et_1);
        etprice = (EditText) findViewById(R.id.et_2);
        etdescription = (EditText) findViewById(R.id.et_3);
        etcolor = (EditText) findViewById(R.id.et_4);
        etsize = (EditText) findViewById(R.id.et_5);


        Bundle extras = getIntent().getExtras(); //get userid
        if (extras != null) {
            merchantid = extras.getString("merchantid");
        }

        //Toast.makeText(AddproductActivity.this,"Response :" + merchantid, Toast.LENGTH_LONG).show();

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddproductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddproductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                cropImage();
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setType("image/*");
//                startActivityForResult(intent, 1);
            }
        });


//        mQueue = Volley.newRequestQueue(AddproductActivity.this);
        //jsonParse();
//        String url = "http://ec2-54-175-85-90.compute-1.amazonaws.com/api/v1/categories";

        //tvSelectedChoices.setText("Fail");


        Call<List<Category>> call = APIManager.getInstance().getAPIService().getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, retrofit2.Response<List<Category>> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    for (Category category : response.body()) {
                        categories.add(category);
                    }
                }
                try {
                    Log.d("Get Categories", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("Get Categories", "failure");
            }
        });

//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    //tvSelectedChoices.setText(response.toString());
//                    //tvSelectedChoices.setText(response.toString());
//                    //Toast.makeText(AddproductActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();
//                    //JSONArray jsonArray = response.getJSONArray("");
//                    JSONArray jsonArray = response;
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        //JSONObject data = jsonArray.getJSONObject(i);
//                        String categories123 = jsonObject.getString("category");
//                        categories.add(categories123);
//                        //tvSelectedChoices.append(categories123 + " ");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    tvSelectedChoices.setText("Fail");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        mQueue.add(request);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSelectedChoices.getText().toString().equals("") ||
                        etname.getText().toString().equals("") ||
                        etprice.getText().toString().equals("")) {
                    Toast.makeText(AddproductActivity.this, "Please input all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    createProduct();
                }
            }
        });


        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAlertDialog();
            }
        });

        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwODFhYjExMGUwM2E0NDdkZTFlYjBkMCIsImlhdCI6MTYxOTM1NTI1NywiZXhwIjoxNjI3MTMxMjU3fQ.AVI5V3g09UIruh47iulr2MU51EOWbnWHclFNDhe3Vvo");

    }

    private void cropImage() {
        CropImage.activity()
                .start(AddproductActivity.this);
    }

    private void createProduct() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", etname.getText().toString().trim());
        data.put("price", etprice.getText().toString().trim());
        data.put("quantity", "50");
        data.put("description", etdescription.getText().toString().trim());
        data.put("style", etStyle.getText().toString().trim());

        Category selectedCategory = null;
        for (Category category: categories) {
            if (tvSelectedChoices.getText().toString().equals(category.getCategory())) {
                selectedCategory = category;
            }
        }

        if (selectedCategory != null) {
            data.put("category", selectedCategory.get_id());
        }

        String[] colors = etcolor.getText().toString().split(",");
        String[] sizes = etsize.getText().toString().split(",");

        data.put("options", new Options(Arrays.asList(colors), Arrays.asList(sizes)));

        Call<Product> call = APIManager.getInstance().getAPIService().createProduct(data);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    showMessage("Create Product Success");

                    Product product = response.body();

                    String content = "";
                    content += product.getName() + "\n";
                    content += product.get_id() + "\n";
                    content += product.getDesc() + "\n";
                    content += product.getPrice() + "\n";


                    showMessage(content);

                    uploadPhoto(product.get_id());
                }
                try {
                    Log.d("Create Product", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("Create Product", "failure");
            }
        });

    }

    private void uploadPhoto(String productId) {
        if (photoUri != null) {
            File photo = new File(photoUri.getPath());

            RequestBody productPhotoBody = RequestBody.create(MediaType.parse("image/*"),
                    photo);
            List<MultipartBody.Part> productPhotosPart = new ArrayList<>();

            productPhotosPart.add(MultipartBody.Part.createFormData("photos",
                    photo.getName(),
                    productPhotoBody));


            Call<Product> call = APIManager.getInstance().getAPIService().updateProduct(productId, productPhotosPart, new HashMap(), new ArrayList(), new ArrayList());

            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        showMessage("Upload Product Success");

                        Product product = response.body();

                        String content = "";
                        content += product.getName() + "\n";
                        content += product.getPhotos().get(0) + "\n";

                        showMessage(content);

                        Intent intent = new Intent(AddproductActivity.this, ShopmainActivity.class);
                        startActivity(intent);
                    }
                    try {
                        Log.d("Upload Product Photo", response.code() + response.errorBody().string() + "");
                    } catch (Exception e) {
                        Log.d("Null message", e.getMessage().toString());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.d("Upload Product Photo", "failure");
                }
            });
        } else {
            Intent intent = new Intent(AddproductActivity.this, ShopmainActivity.class);
            startActivity(intent);
        }

    }

    private void updateProduct(String productId) {
        if (photoUri != null) {
            File photo = new File(photoUri.getPath());
            Log.d("Photo", photo.getName());
            RequestBody productPhotoBody = RequestBody.create(MediaType.parse("image/*"),
                    photo);
            List<MultipartBody.Part> productPhotosPart = new ArrayList<>();

            productPhotosPart.add(MultipartBody.Part.createFormData("photos",
                    photo.getName(),
                    productPhotoBody));

            Log.d("Photo List", productPhotosPart.get(0).toString());

            Map<String, RequestBody> data = new HashMap<>();
            data.put("name", createPartFromString("New "));
            data.put("price", createPartFromString("1000"));
            data.put("quantity", createPartFromString("100"));
            data.put("description", createPartFromString("this is so so so new 2"));

            List<RequestBody> color = Arrays.asList(createPartFromString("red"), createPartFromString("green"), createPartFromString("blue"));

            Map<String, Options> options = new HashMap<>();
            options.put("options", new Options(Arrays.asList("red", "green", "blue"), Arrays.asList("X", "XL")));

            Call<Product> call = APIManager.getInstance().getAPIService().updateProduct("608556b5ea494132ec32b3d5", productPhotosPart, data, color, null);

            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        showMessage("Update Product Success");

                        Product product = response.body();

                        String content = "";
                        content += product.getName() + "\n";
                        content += product.getPhotos().get(0) + "\n";

                        showMessage(content);
                    }
                    try {
                        Log.d("Update Product Photo", response.code() + response.errorBody().string() + "");
                    } catch (Exception e) {
                        Log.d("Null message", e.getMessage().toString());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.d("Update Product Photo", "failure");
                }
            });
        }

        Intent intent = new Intent(AddproductActivity.this, ShopmainActivity.class);
        startActivity(intent);
    }

    private void showMessage(String message) {
        Toast.makeText(AddproductActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            // Here set image preview

            photoUri = result.getUri();
            ivimage.setImageURI(photoUri);
//            post_image.setImageURI(imageUri);
//            image_label.setText("");

        }

//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            //ivimage = (ImageView) findViewById(R.id.product_image);
//            List<Bitmap> bitmaps = new ArrayList<>();
//            ClipData clipData = data.getClipData();
//            if (clipData != null) {
//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    Uri imageUri = clipData.getItemAt(i).getUri();
//
//                    Toast.makeText(AddproductActivity.this, "Response :" + imageUri.toString(), Toast.LENGTH_LONG).show();
//
//                    try {
//                        InputStream is = getContentResolver().openInputStream(imageUri);
//                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        bitmaps.add(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                Uri imageUri = data.getData();
//                try {
//                    InputStream is = getContentResolver().openInputStream(imageUri);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    bitmaps.add(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (final Bitmap b : bitmaps) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ivimage.setImageBitmap(b);
//                            }
//                        });
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        }
    }

    private void CreateAlertDialog() {
        list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Items");

        String strtext[] = new String[categories.size()];
        for (int j = 0; j < categories.size(); j++) {
            strtext[j] = categories.get(j).getCategory();
        }

        //String[] strtext = {"checkbox1", "checkbox2", "checkbox3", "checkbox4", "checkbox5"};
        builder.setMultiChoiceItems(strtext, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String arr[] = strtext;
                if (isChecked) {
                    list.add(arr[which]);
                } else if (list.contains(arr[which])) {
                    list.remove(arr[which]);
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = "";
                int i = 0;
                for (String item : list) {
                    if (i < list.size() && i != 0) {
                        data = data + " " + item;
                    } else {
                        data = data + item;
                    }
                    i++;
                }

                tvSelectedChoices.setText(/*"Selected category: " + */data);
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }/*
    private void CreateAlertDialoge2(){
        list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Items");

        String[] strtext = {"checkbox6", "checkbox7", "checkbox8", "checkbox9", "checkbox10"};
        builder.setMultiChoiceItems(strtext, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String arr[] = strtext;
                if (isChecked){
                    list.add(arr[which]);
                }
                else if (list.contains(arr[which])){
                    list.remove(arr[which]);
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = "";
                int i = 0;
                for (String item:list){
                    if (i < list.size() && i != 0){
                        data = data + " " + item;
                    }
                    else {
                        data = data + item;
                    }
                    i++;
                }

                tvSelectedChoices2.setText("Selected style: " + data);
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }*/


}