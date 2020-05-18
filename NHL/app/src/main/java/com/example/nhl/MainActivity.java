package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private EditText textViewSearch;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        textViewSearch = findViewById(R.id.inputNickname);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(manager);
        recyclerViewUsers.setNestedScrollingEnabled(false);

    }

    public void showUsers(View view) {
        String value = textViewSearch.getText().toString();
        if (value.isEmpty()){
            getUsers();
        } else {
            searchUsers(value);
        }
    }

    public void getUsers() {

        NetworkService.getInstance()
                .getJSONApi()
                .getUsers()
                .enqueue(new Callback<List<User>>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        userList = response.body();
                        adapter = new UserAdapter(userList);
                        adapter.setOnUserClickListener(position -> {});
                        recyclerViewUsers.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    public void searchUsers(String value) {

        NetworkService.getInstance()
                .getJSONApi()
                .searchUsers(value)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        List<User>  users = response.body();
                        adapter = new UserAdapter(users);
                        recyclerViewUsers.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void addUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
    }


