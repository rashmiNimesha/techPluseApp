package com.example.techpluseapplication;

public class NewsItem {
    private String title;
    private String imageUrl;
    private String description;
    private String category;
    private String date;
    private String longDescription;

    public NewsItem() {
    }

    public NewsItem(String title, String description, String imageUrl, String longDescription) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.longDescription = longDescription;
    }

    public String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public String getLongDescription() {
        return longDescription;
    }
    public String getCategory() {
        return category;
    }
    public String getDate() {
        return date;
    }
}
