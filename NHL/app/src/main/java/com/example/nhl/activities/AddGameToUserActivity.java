package com.example.nhl.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhl.MainActivity;
import com.example.nhl.model.GameDto;
import com.example.nhl.network.NetworkService;
import com.example.nhl.R;
import com.example.nhl.model.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameToUserActivity extends AppCompatActivity {

    private EditText textMy;
    private EditText textUs;
    private EditText textPlayer;
    private RadioGroup wonLoseGroup;
    private String id;
    private CheckBox overtimeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_to_user);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        textMy = findViewById(R.id.textMyScore);
        textUs = findViewById(R.id.textOpponentScore);
        wonLoseGroup = findViewById(R.id.radioGroupWeWon);
        overtimeCheckbox = findViewById(R.id.checkBoxOvertime);
    }

    @SuppressLint("ResourceAsColor")
    public void addGame(View view) {
        int radioButtonId = wonLoseGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        String result = textMy.getText().toString() + ":" + textUs.getText().toString();
        boolean win = true;
        if (radioButton.getText().equals("Проиграли")) {
            win = false;
        }
        boolean overtime = overtimeCheckbox.isChecked();
        GameDto game = new GameDto(result, overtime, win);
        postScores(game);
    }

    public void postScores(GameDto game) {

        NetworkService.getInstance()
                .getJSONScoreApi()
                .setScore(id, game)
                .enqueue(new Callback<Game>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(@NonNull Call<Game> call, @NonNull Response<Game> response) {

                        if (response.isSuccessful()){
                            Toast toast = Toast.makeText(getApplicationContext(), "игра добавлена", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundColor(R.color.colorPrimaryDark);
                            toast.show();
                            goToMainPage();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<Game> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void goToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
