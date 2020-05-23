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
import com.example.nhl.model.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameToUserActivity extends AppCompatActivity {

    private EditText textMy;
    private EditText textUs;
    private EditText textPlayer;
    private RadioGroup wonLoseGroup;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_to_user);
        Intent intent  = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        textMy = findViewById(R.id.textMyScore);
        textUs = findViewById(R.id.textOpponentScore);
        textPlayer = findViewById(R.id.editTextPlayer);
        wonLoseGroup = findViewById(R.id.radioGroupWeWon);
    }

    @SuppressLint("ResourceAsColor")
    public void addGame(View view) {
        int radioButtonId = wonLoseGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        boolean win = true;
        if (radioButton.getText().equals("Проиграли")){
            win = false;
        }
        Score score = new Score(
                textMy.getText().toString() + ":"+textUs.getText().toString(),textPlayer.getText().toString(), win
        );
        postScores(score);
        goToMainPage(view);
    }

    public void postScores(Score score) {

        NetworkService.getInstance()
                .getJSONScoreApi()
                .setScore(id,score)
                .enqueue(new Callback<List<Score>>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                        List<Score> list = response.body();
                        Toast toast = Toast.makeText(getApplicationContext(), "игра добавлена", Toast.LENGTH_LONG);
                        toast.getView().setBackgroundColor(R.color.colorPrimaryDark);
                        toast.show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void goToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
