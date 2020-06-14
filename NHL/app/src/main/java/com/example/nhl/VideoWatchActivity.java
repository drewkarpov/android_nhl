package com.example.nhl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class VideoWatchActivity extends AppCompatActivity {

    private VideoView videoView;
    ProgressDialog pDialog;
    ImageView previewImageView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        videoView = findViewById(R.id.videoView);
        previewImageView = findViewById(R.id.previewImage);

        Intent intent = getIntent();
        String videoPath = intent.getStringExtra("video");
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Video Streaming ");
        pDialog.setMessage("buffering ...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();


        try {
            final MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoView);
            final Uri video = Uri.parse(videoPath);
            videoView.setMediaController(mediacontroller);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    pDialog.dismiss();
                    mp.start();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                    mp.pause();
                    mp.setOnCompletionListener(this);
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


    }
}

