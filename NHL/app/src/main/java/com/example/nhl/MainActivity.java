package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.example.nhl.activities.AddUserActivity;
import com.example.nhl.activities.PopUpAddActivity;
import com.example.nhl.network.NetworkService;
import com.example.nhl.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private EditText textViewSearch;
    private UserAdapter adapter;
    public static List<User> userList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        textViewSearch = findViewById(R.id.inputNickname);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(manager);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        if (userList.size() != 0) {
            adapter = new UserAdapter(userList);
            recyclerViewUsers.setAdapter(adapter);
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getUsers();
            swipeRefreshLayout.setRefreshing(false);
        });

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
                        adapter.setOnUserClickListener((int position) -> {
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
                            openPopup(value);
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

    public void openPopup(String value ) {
        Intent intent = new Intent(MainActivity.this, PopUpAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("value",value);
        startActivity(intent);
    }


    public void addUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    public void goVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }
}


