package com.example.nhl.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.nhl.MainActivity;
import com.example.nhl.helpers.StatusGenerator;
import com.example.nhl.model.UserDto;
import com.example.nhl.network.NetworkService;
import com.example.nhl.R;
import com.example.nhl.model.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextComment;
    private String nickname = "";
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getStringExtra("name") == null) {
            nickname = "";
        } else {
            nickname = intent.getStringExtra("name");
        }
        setContentView(R.layout.activity_add_user);
        editTextName = findViewById(R.id.editTextName);
        editTextComment = findViewById(R.id.editTextDescription);
        ratingBar = findViewById(R.id.ratingBar);
        editTextName.setText(nickname);

    }


    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    public void addUserToBd(View view) {
        double rating = ratingBar.getRating();
        String status = StatusGenerator.getStatusByRating(rating);
        String comment = " ";
        if (!editTextComment.getText().toString().equals("")){
            comment = editTextComment.getText().toString();
        }
        User user = new User(
                editTextName.getText().toString(),
                status,
                comment,
                StatusGenerator.getRating(status)
        );
        postUsers(user);
        Toast toast = Toast.makeText(getApplicationContext(), "игрок добавлен", Toast.LENGTH_LONG);
        toast.show();
        goToMainPage(view);
    }

    public void postUsers(User user) {

        NetworkService.getInstance()
                .getJSONApi()
                .postUser(user.mapToDto())
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    public void goToAddGamePage(View view) {
        Intent intent = new Intent(this, AddGameToUserActivity.class);
        startActivity(intent);
    }
}

