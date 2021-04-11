package com.example.nhl.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhl.model.GameDto;
import com.example.nhl.network.NetworkService;
import com.example.nhl.R;
import com.example.nhl.model.Game;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameToUserActivity extends AppCompatActivity {

    private RadioGroup wonLoseGroup;
    private String id;
    private CheckBox overtimeCheckbox;
    private NumberPicker numberPicker;
    private NumberPicker numberPickerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_to_user);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        wonLoseGroup = findViewById(R.id.radioGroupWeWon);
        overtimeCheckbox = findViewById(R.id.checkBoxOvertime);

        numberPicker = findViewById(R.id.numberPicker);
        numberPickerTwo = findViewById(R.id.numberPickerTwo);

        if (numberPicker != null) {
            numberPicker.setValue(0);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(20);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numberPicker.setTag(newVal);
                }
            });
        }
        if (numberPickerTwo != null) {
            numberPickerTwo.setValue(0);
            numberPickerTwo.setMinValue(0);
            numberPickerTwo.setMaxValue(20);
            numberPickerTwo.setWrapSelectorWheel(true);
            numberPickerTwo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numberPickerTwo.setTag(newVal);
                }
            });
        }
    }

    @SuppressLint("ResourceAsColor")
    public void addGame(View view) {
        int radioButtonId = wonLoseGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        String result = String.format("%d:%d", numberPicker.getValue(), numberPickerTwo.getValue());
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

                        if (response.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "игра добавлена", Toast.LENGTH_LONG);
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
