package com.example.nhl.model;

public class VideoItem {

    private String videoUrl;
    private String prewiewUrl;

    public VideoItem(String videoUrl, String prewiewUrl) {
        this.videoUrl = videoUrl;
        this.prewiewUrl = prewiewUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPrewiewUrl() {
        return prewiewUrl;
    }

    public void setPrewiewUrl(String prewiewUrl) {
        this.prewiewUrl = prewiewUrl;
    }
}
