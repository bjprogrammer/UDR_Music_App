package com.example.bobby.udr;

public class Newsdata {
    private String title, time,source, content;

    public Newsdata() {
    }

    public Newsdata(String title, String source, String time, String content) {
        this.title = title;
        this.source = source;
        this.time = time;
        this.content=content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

