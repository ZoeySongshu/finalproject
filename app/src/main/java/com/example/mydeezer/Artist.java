package com.example.mydeezer;

import java.io.Serializable;

public class Artist implements Serializable {
    String id;
    String name;
    String link;
    String picture;
    String nb_album;
    String nb_fan;
    String radio;
    String tracklist;
    boolean isCollection;

    public Artist(String id, String name, String link, String picture, String nb_album, String nb_fan, String radio, String tracklist, boolean isCollection) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.nb_album = nb_album;
        this.nb_fan = nb_fan;
        this.radio = radio;
        this.tracklist = tracklist;
        this.isCollection = isCollection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNb_album() {
        return nb_album;
    }

    public void setNb_album(String nb_album) {
        this.nb_album = nb_album;
    }

    public String getNb_fan() {
        return nb_fan;
    }

    public void setNb_fan(String nb_fan) {
        this.nb_fan = nb_fan;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                ", nb_album='" + nb_album + '\'' +
                ", nb_fan='" + nb_fan + '\'' +
                ", radio='" + radio + '\'' +
                ", tracklist='" + tracklist + '\'' +
                ", isCollection=" + isCollection +
                '}';
    }
}
