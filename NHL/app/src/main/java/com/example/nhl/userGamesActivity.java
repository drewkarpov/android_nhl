package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nhl.model.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userGamesActivity extends AppCompatActivity {

    private ArrayAdapter<Score> adapter;
    private ListView listView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score);
        listView = findViewById(R.id.listViewGames);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }


    public void getScores() {
        NetworkService.getInstance()
                .getJSONScoreApi()
                .getScoresById(id)
                .enqueue(new Callback<List<Score>>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                        List<Score> scores = response.body();
                        assert scores != null;
                        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_game_card, scores);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
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
        startActivity(intent);
    }

    public void reNewClick(View view) {
        getScores();
    }
}
