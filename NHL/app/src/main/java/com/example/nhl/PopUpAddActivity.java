package com.example.nhl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class PopUpAddActivity extends AppCompatActivity {
    private String nickname;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_add);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.3));
        Intent intent = getIntent();
        nickname = intent.getStringExtra("value");

        textView = findViewById(R.id.textViewPopup);
        String text = String.format("Игрок '%s' не найден! \nДобавить игрока в базу?",nickname);
        textView.setText(text);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void goToAddUser(View view) {
        Intent intent = new Intent(this, AddUserActivity.class);
        intent.putExtra("name", nickname);
        startActivity(intent);
    }
}
