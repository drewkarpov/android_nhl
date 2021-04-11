package com.example.nhl.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nhl.helpers.StatusGenerator;
import com.example.nhl.network.NetworkService;
import com.example.nhl.R;
import com.example.nhl.model.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textComment;
    private Intent intentMainPage;
    private String status;
    private RatingBar ratingBar;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        textName = findViewById(R.id.textChangeName);
        textComment = findViewById(R.id.textChangeComment);
        ratingBar = findViewById(R.id.ratingBar2);
        intentMainPage = new Intent(this, MainActivity.class);
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        String name = intent.getStringExtra("name");
        String comment = intent.getStringExtra("comment");
        id = intent.getStringExtra("id");
        ratingBar.setRating(StatusGenerator.getRatingByStatus(status));
        textName.setText(name, TextView.BufferType.EDITABLE);
        textComment.setText(comment, TextView.BufferType.EDITABLE);
    }

    public void changeUserData(View view) {
        double rating = ratingBar.getRating();
        String status = StatusGenerator.getStatusByRating(rating);
        String comment = " ";
        if (!textComment.getText().toString().equals("")){
            comment = textComment.getText().toString();
        }
        User user = new User(
                textName.getText().toString(),
                status,
                comment,
                StatusGenerator.getRating(status)
        );
        changeDataUser(user);
    }

    public void deleteUser(View view) {
        deleteUserFromBd();
    }

    public void deleteUserFromBd() {
        NetworkService.getInstance()
                .getJSONApi()
                .deleteUser(id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "игрок удален", Toast.LENGTH_LONG).show();
                            startActivity(intentMainPage);
                        } else {
                            Toast.makeText(getApplicationContext(), "ошибка сервера "+ response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void changeDataUser(User user) {
        NetworkService.getInstance()
                .getJSONApi()
                .changeUser(user.mapToDto(), id)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "данные игрока изменены", Toast.LENGTH_LONG).show();
                            startActivity(intentMainPage);
                        } else {
                            Toast.makeText(getApplicationContext(), "ошибка сервера "+ response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void returnToMainPage(View view) {
        Intent intent = new Intent(this, UserDataActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
    }
}
