package com.example.bobby.udr;

public class commentshelper {
    private String name,comments,url,timestamp;
    int rating;

    public commentshelper() {
    }

    public commentshelper(String name, String comments, String url,int rating,String timestamp) {
        this.name = name;
        this.comments = comments;
        this.url = url;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}