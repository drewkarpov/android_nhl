package com.example.nhl.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nhl.R;


@SuppressLint("Registered")
public class UserDataActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textComment;
    private  String status;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        textName = findViewById(R.id.textName);
        textComment = findViewById(R.id.textComment);
        Intent intent = getIntent();
        textName.setText(intent.getStringExtra("name"));
        id = intent.getStringExtra("id");
        textComment.setText(intent.getStringExtra("comment"));
        status = intent.getStringExtra("status").toLowerCase();
    }

    public void returnToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeUserData(View view) {
        Intent intent = new Intent(this, ChangeUserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", textName.getText());
        intent.putExtra("comment", textComment.getText());
        intent.putExtra("status", status);
        startActivity(intent);
    }

    public void goGamesPage(View view) {
        Intent intent = new Intent(this, UserGamesActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("status", status);
        startActivity(intent);
    }


}
