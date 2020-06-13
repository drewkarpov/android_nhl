package com.example.nhl.model;

public class VideoItem {

    private String videoUrl;
    private String prewiewUrl;
    private final String host = "http://89.223.88.248/";

    public VideoItem(String videoUrl, String prewiewUrl) {
        this.videoUrl = host + videoUrl;
        this.prewiewUrl =host + prewiewUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = host + videoUrl;
    }

    public String getPrewiewUrl() {
        return prewiewUrl;
    }

    public void setPrewiewUrl(String prewiewUrl) {
        this.prewiewUrl = host + prewiewUrl;
    }
}
