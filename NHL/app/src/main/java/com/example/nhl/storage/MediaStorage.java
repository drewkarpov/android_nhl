package com.example.nhl.storage;

import com.example.nhl.model.VideoItem;

import java.util.ArrayList;

public class MediaStorage {

    public ArrayList<VideoItem> getVideoItems() {

        ArrayList<VideoItem> items = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            items.add(new VideoItem("video_" + i + ".mp4", "image_"+ i + ".png"));
        }
        return items;
    }
}