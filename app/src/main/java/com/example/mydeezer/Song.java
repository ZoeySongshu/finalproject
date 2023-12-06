package com.example.mydeezer;

public class Song {
    String id;

    String title;

    String duration;
    String albumTitle;
    String cover;
    int is_collection;

    public Song(String id, String title, String duration, String albumTitle, String cover) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.cover = cover;
        is_collection=0;
    }

    public Song(String id, String title, String duration, String albumTitle, String cover, int is_collection) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.cover = cover;
        this.is_collection = is_collection;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", albumTitle='" + albumTitle + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
