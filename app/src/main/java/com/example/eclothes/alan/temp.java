package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.eclothes.R;

public class temp extends AppCompatActivity {

    Button go, go2, go3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        go = findViewById(R.id.go);
        go.setOnClickListener(v -> {
            Intent intent = new Intent(temp.this, Favourite.class);
            startActivity(intent);
        });

        go2 = findViewById(R.id.go2);
        go2.setOnClickListener(v -> {
            Intent intent = new Intent(temp.this, Edit_Profile_Shop.class);
            startActivity(intent);
        });

        go3 = findViewById(R.id.go3);
        go3.setOnClickListener(v -> {
            Intent intent = new Intent(temp.this, Edit_Profile_User.class);
            startActivity(intent);
        });
    }
}