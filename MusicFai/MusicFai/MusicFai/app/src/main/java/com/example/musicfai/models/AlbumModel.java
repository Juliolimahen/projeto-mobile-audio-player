package com.example.musicfai.models;

import android.net.Uri;

public class AlbumModel {
    private String path;
    private String title;
    private String artista;
    private String album;
    private Uri uri;

    public AlbumModel(String path, String title, String artista, String album, Uri uri) {
        this.path = path;
        this.title = title;
        this.artista = artista;
        this.album = album;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
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

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
