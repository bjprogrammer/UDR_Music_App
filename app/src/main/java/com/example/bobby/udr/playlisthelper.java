package com.example.bobby.udr;

public class playlisthelper {
    private String title, artist, year,ID;

    public playlisthelper() {
    }

    public playlisthelper(String title, String artist, String year,String ID) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
