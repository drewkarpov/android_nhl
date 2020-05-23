package com.example.nhl;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.nhl.model.Score;
import java.util.ArrayList;
import java.util.List;


public class UserDataActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textId;
    private TextView textComment;
    private static String status;

    static final String EXTRA_POS = "my_item_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        textName = findViewById(R.id.textName);
        textId = findViewById(R.id.textId);
        textComment = findViewById(R.id.textComment);
        Intent intent = getIntent();
        textName.setText(intent.getStringExtra("name"));
        textId.setText(intent.getStringExtra("id"));
        textComment.setText(intent.getStringExtra("comment"));
        status = intent.getStringExtra("status");
    }

    public void returnToMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeUserData(View view) {
        Intent intent = new Intent(this, ChangeUserActivity.class);
        intent.putExtra("id", textId.getText());
        intent.putExtra("name", textName.getText());
        intent.putExtra("comment", textComment.getText());
        intent.putExtra("status", status);
        startActivity(intent);
    }

    public void goGamesPage(View view) {
        Intent intent = new Intent(this, userGamesActivity.class);
        intent.putExtra("id", textId.getText());
        startActivity(intent);
    }

}
