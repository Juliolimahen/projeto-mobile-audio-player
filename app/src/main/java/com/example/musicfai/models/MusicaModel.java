package com.example.musicfai.models;


import android.net.Uri;

import java.io.Serializable;

public class MusicaModel implements Serializable {
    private String path;
    private String title;
    private String duration;
    private String Artista;
    private String Album ;

    public MusicaModel(String path, String title, String duration, String artista, String album) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        Artista = artista;
        Album = album;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}