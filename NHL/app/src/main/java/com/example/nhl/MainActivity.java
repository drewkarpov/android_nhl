package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.example.nhl.activities.AddUserActivity;
import com.example.nhl.activities.PopUpAddActivity;
import com.example.nhl.network.NetworkService;
import com.example.nhl.model.User;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter adapter;
    public static List<User> userList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private JsonObject statistic;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stat stat = new Stat();
        stat.execute();
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        searchView = findViewById(R.id.SearchView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(manager);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        adapter = new UserAdapter(userSearch());
        recyclerViewUsers.setAdapter(adapter);
            swipeRefreshLayout.setOnRefreshListener(() -> {
            getUsers();
            swipeRefreshLayout.setRefreshing(false);
        });

    }


    private List<User> userSearch() {
        List<User> list = new ArrayList<>();
        list.add(new User("kek", "pek", "dedwed"));
        list.add(new User("cdc", "pek", "dedwed"));
        list.add(new User("vvvv", "pek", "dedwed"));
        list.add(new User("ddd", "pek", "dedwed"));
        return list;
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

    public void getStatistic() {
        NetworkService.getInstance()
                .getJSONApi()
                .usersStatistic()
                .enqueue(new Callback<JsonObject>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        statistic = response.body();
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void openPopup(String value) {
        Intent intent = new Intent(MainActivity.this, PopUpAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("value", value);
        startActivity(intent);
    }


    public void addUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        intent.putExtra("name", searchView.getQuery().toString());
        startActivity(intent);
    }

    public void goVideo(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void goStatistic(View view) {

        Intent intent = new Intent(this, StatisticActivity.class);
        intent.putExtra("zero", statistic.getAsJsonPrimitive("zero").toString());
        intent.putExtra("low", statistic.getAsJsonPrimitive("low").toString());
        intent.putExtra("hard", statistic.getAsJsonPrimitive("hard").toString());
        intent.putExtra("driver", statistic.getAsJsonPrimitive("driver").toString());
        startActivity(intent);
    }

    private class Stat extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getStatistic();
            getUsers();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }




}


