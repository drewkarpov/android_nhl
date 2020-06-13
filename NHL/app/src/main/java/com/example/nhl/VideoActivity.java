package com.example.nhl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import com.example.nhl.model.VideoItem;
import com.example.nhl.storage.MediaStorage;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private ArrayList<VideoItem> videoItems = new ArrayList<>();
    private ProgressBar progressBar;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_menu);

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerViewUVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(videoItems, this);
        recyclerView.setAdapter(videoAdapter);
        Content content = new Content();
        content.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this, android.R.anim.fade_out));
            videoAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                MediaStorage storage = new MediaStorage();
                videoItems.addAll(storage.getVideoItems());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

}
