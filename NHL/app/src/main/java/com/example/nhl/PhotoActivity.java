package com.example.nhl;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nhl.activities.AddUserActivity;
import com.example.nhl.model.User;
import com.example.nhl.network.NetworkService;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = findViewById(R.id.imageViewTextImage);
        imageView.setDrawingCacheEnabled(true);
        textView = findViewById(R.id.textViewFromImage);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            int orientation = getResources().getConfiguration().orientation;
            int degress = 0;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.w("test","dont touch degrees");
            } else {
               degress = 90;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(degress);


            Bitmap photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            assert photo != null;
            Bitmap finalBitmap = Bitmap.createBitmap(photo, 0, 0,
                    photo.getWidth(), photo.getHeight(),
                    matrix, true);
            imageView.setImageBitmap(finalBitmap);
            goToAddUser();
        }
    }


    public void goToAddUser(){
        Intent intent = new Intent(this, AddUserActivity.class);
        intent.putExtra("name", getTextFromImage());
        startActivity(intent);
    }


    public String getTextFromImage() {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "could not have text", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < items.size(); i++) {
                TextBlock item = items.valueAt(i);
                sb.append(item.getValue());
                sb.append("\n");
            }
            return sb.toString();
        }
        return "";
    }
}