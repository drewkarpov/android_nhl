package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhl.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextComment;
    private RadioGroup radioGroupPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        editTextName = findViewById(R.id.editTextName);
        editTextComment = findViewById(R.id.editTextDescription);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

    }


    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    public void addUserToBd(View view) {
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);

        User user = new User(
                editTextName.getText().toString(),
                radioButton.getText().toString().toLowerCase(),
                editTextComment.getText().toString()
        );
        postUsers(user);
        Toast toast = Toast.makeText(getApplicationContext(), "игрок добавлен", Toast.LENGTH_LONG);
        toast.show();
        goToMainPage(view);
    }

    public void postUsers(User user) {

        NetworkService.getInstance()
                .getJSONApi()
                .postUser(user)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


}

