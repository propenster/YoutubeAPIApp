package com.github.propenster.youtubeapiapp.Models;

public class VideoDetails {
    private String videoId, title, description, imageUrl;

    public VideoDetails(String videoId, String title, String description, String imageUrl) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public VideoDetails() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
