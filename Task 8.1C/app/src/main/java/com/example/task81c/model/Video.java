package com.example.task81c.model;

public class Video {
    private int videoId;
    private int userId;
    private String videoURL;

    public Video(int userId, String videoURL) {
        this.userId = userId;
        this.videoURL = videoURL;
    }

    public Video(int videoId, int userId, String videoURL) {
        this.videoId = videoId;
        this.userId = userId;
        this.videoURL = videoURL;
    }

    public int getUserId() {
        return userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

}
