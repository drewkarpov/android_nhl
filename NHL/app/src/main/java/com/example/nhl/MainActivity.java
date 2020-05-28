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
import android.widget.Toast;

import com.example.nhl.model.User;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private EditText textViewSearch;
    private UserAdapter adapter;
    private static List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        textViewSearch = findViewById(R.id.inputNickname);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(manager);
        recyclerViewUsers.setNestedScrollingEnabled(false);
        if (userList.size() != 0) {
            adapter = new UserAdapter(userList);
            recyclerViewUsers.setAdapter(adapter);
        }

    }

    public void showUsers(View view) {
        String value = textViewSearch.getText().toString();
        if (value.isEmpty()) {
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
                        adapter.setOnUserClickListener(position -> {
                        });
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
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        List<User> users = response.body();
                        if (users.size() != 0) {
                            adapter = new UserAdapter(users);
                            recyclerViewUsers.setAdapter(adapter);
                            Toast toast = Toast.makeText(getApplicationContext(), "найдено " + users.size() + " пользователей", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "пользователи с таким никнеймом не найдены", Toast.LENGTH_LONG);
                            toast.show();
                        }
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


