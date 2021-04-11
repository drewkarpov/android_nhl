package com.example.nhl.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhl.MainActivity;
import com.example.nhl.network.NetworkService;
import com.example.nhl.R;
import com.example.nhl.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class UserGamesActivity extends AppCompatActivity {

    private ArrayAdapter<Game> adapter;
    private ListView listView;
    private String id;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score);
        listView = findViewById(R.id.listViewGames);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        status = intent.getStringExtra("status");
        GamesAsyncTask task = new GamesAsyncTask();
        task.execute();
    }


    public void getScores() {
        NetworkService.getInstance()
                .getJSONScoreApi()
                .getScoresById(id)
                .enqueue(new Callback<List<Game>>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                        List<Game> scores = response.body();
                        if (scores != null && scores.size() != 0){
                            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_game_card, scores);
                            listView.setAdapter(adapter);
                        }else {
                            Toast toast = Toast.makeText(getApplicationContext(), "список игр пуст", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    public void returnToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToAddGamePage(View view) {
        Intent intent = new Intent(this, AddGameToUserActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void returnGamePage(View view) {
        Intent intent = new Intent(this, UserDataActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
    }

    public void reNewClick(View view) {
        getScores();
    }

    private class GamesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getScores();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
