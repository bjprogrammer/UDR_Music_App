package com.example.bobby.udr;

/**
 * Created by Bobby on 5/28/2016.
 */
public class radiostreaming {
    private String title, language, url;

    public radiostreaming() {
    }

    public radiostreaming(String title, String language,String url) {
        this.title = title;
        this.language = language;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

