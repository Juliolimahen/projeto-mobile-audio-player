package com.example.musicfai.models;

import android.media.MediaPlayer;

public class MediaPlayerModel {
    private static MediaPlayer instance;

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }

    private static int currentIndex;

    static {
        setCurrentIndex(-1);
    }

    public static void setInstance(MediaPlayer instance) {
        MediaPlayerModel.instance = instance;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public static void setCurrentIndex(int currentIndex) {
        MediaPlayerModel.currentIndex = currentIndex;
    }
}
