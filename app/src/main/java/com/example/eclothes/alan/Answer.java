package com.example.eclothes.alan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eclothes.R;

import java.util.Objects;

public class Answer extends AppCompatActivity {

    ImageView productpic, profilepic_user;
    TextView title, price, username, question;
    EditText answer;
    Button reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Answer");

        productpic = findViewById(R.id.answer_productpic_user);
        profilepic_user = findViewById(R.id.answer_profilepic_user);
        title = findViewById(R.id.answer_title);
        price = findViewById(R.id.answer_price);
        username = findViewById(R.id.answer_username);
        question = findViewById(R.id.answer_question);
        answer = findViewById(R.id.answerET_answer);
        reply = findViewById(R.id.answer_answer);

    }
}