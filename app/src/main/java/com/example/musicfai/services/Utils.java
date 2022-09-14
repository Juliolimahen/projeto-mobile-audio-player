package com.example.musicfai.services;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static class Milisegundos {
        public static String converterEmMilisegundos() {
            return converterEmMilisegundos();
        }

        public static String converterEmMilisegundos(String duration) {
            long millis = Long.parseLong(duration);
            return String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        }
    }
}
