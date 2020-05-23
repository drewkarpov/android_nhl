package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nhl.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textComment;
    private RadioGroup radioGroupStatus;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonHard;
    private RadioButton radioButtonDriver;
    private Intent intentMainPage;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        textName = findViewById(R.id.textChangeName);
        textComment = findViewById(R.id.textChangeComment);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonHard = findViewById(R.id.radioButtonHard);
        radioButtonDriver = findViewById(R.id.radioButtonDriver);
        intentMainPage = new Intent(this, MainActivity.class);
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        String name = intent.getStringExtra("name");
        String comment = intent.getStringExtra("comment");
         id = intent.getStringExtra("id");
        switch (status){
            case "low": radioButtonLow.setChecked(true);
            break;
            case "hard": radioButtonHard.setChecked(true);
            break;
            case "driver": radioButtonDriver.setChecked(true);
            break;
        }
        textName.setText(name, TextView.BufferType.EDITABLE);
        textComment.setText(comment, TextView.BufferType.EDITABLE);
    }

    public void changeUserData(View view) {
        int radioButtonId = radioGroupStatus.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);

        User user = new User(
                textName.getText().toString(),
                radioButton.getText().toString(),
                textComment.getText().toString()
        );
        changeDataUser(user);
    }

    public void deleteUser(View view) {
        deleteUserFromBd();
    }
    public void deleteUserFromBd(){
        NetworkService.getInstance()
                .getJSONApi()
                .deleteUser(id)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        Toast.makeText(getApplicationContext(), "игрок удален", Toast.LENGTH_LONG).show();
                        startActivity(intentMainPage);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    public void changeDataUser(User user){
        NetworkService.getInstance()
                .getJSONApi()
                .changeUser(user,id)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        Toast.makeText(getApplicationContext(), "данные игрока изменены", Toast.LENGTH_LONG).show();
                        startActivity(intentMainPage);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void returnToMainPage(View view) {
        Intent intent = new Intent(this, UserDataActivity.class);
        startActivity(intent);
    }
}
