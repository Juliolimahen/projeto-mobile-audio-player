package com.example.musicfai.models;


import android.net.Uri;

import java.io.Serializable;

public class MusicaModel implements Serializable {
    private String path;
    private String title;
    private String duration;
    private String artista;
    private String album;
    private transient Uri uri;
    private String uriStr;
    private transient Uri artworkUri;
    private String artworkUristr;

    public MusicaModel(String path, String title, String duration, String artista, String album, Uri uri, Uri artworkUri) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.artista = artista;
        this.album = album;
        this.uri = uri;
        this.artworkUri = artworkUri;
        //Tornar serializavel
        this.artworkUristr = artworkUri.toString();
        //Tornar serializavel
        this.uriStr = uri.toString();
    }

    public String getUriStr() {
        return uriStr;
    }

    //Sobrecarga Teste
    //teste serializacao
    public MusicaModel(String path, String title, String duration, String artista, String album) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.artista = artista;
        this.album = album;
    }

    public String getArtworkUristr() {
        return artworkUristr;
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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getArtworkUri() {
        return artworkUri;
    }

    public void setArtworkUri(Uri artworkUri) {
        this.artworkUri = artworkUri;
    }
}