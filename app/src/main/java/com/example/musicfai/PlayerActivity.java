package com.example.musicfai;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicfai.models.MusicaModel;
import com.example.musicfai.models.MediaPlayerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class PlayerActivity extends AppCompatActivity {

    private TextView titulo, tempoAtual, tempoTotal, musicaArtista, musicaAlbum;
    private SeekBar seekBar;
    private ImageView pausePlay, nextBtn, previousBtn, musicaIco;
    private ArrayList<MusicaModel> musicasList;
    private MusicaModel currentSong;
    private MediaPlayer mediaPlayer = MediaPlayerModel.getInstance();
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        tempoAtual = findViewById(R.id.tempo_atual);
        tempoTotal = findViewById(R.id.tempo_total);
        titulo = findViewById(R.id.musica_titulo);
        seekBar = findViewById(R.id.seek_bar);
        musicaIco = findViewById(R.id.musica_icon);
        musicaArtista = findViewById(R.id.musica_artista);
        musicaAlbum = findViewById(R.id.musica_album);

        titulo.setSelected(true);

        musicasList = (ArrayList<MusicaModel>) getIntent().getSerializableExtra("LIST");

        setMusicarPlayer();

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tempoAtual.setText(converterEmMilisegundos(mediaPlayer.getCurrentPosition() + ""));

                    if (mediaPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.ic_pause);
                        musicaIco.setRotation(x++);
                    } else {
                        pausePlay.setImageResource(R.drawable.ic_play);
                        musicaIco.setRotation(0);
                    }

                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setMusicarPlayer() {
        currentSong = musicasList.get(MediaPlayerModel.getCurrentIndex());
        titulo.setText(currentSong.getTitle());
        if (currentSong.getArtista().equalsIgnoreCase(null))
            musicaArtista.setText("Artista");
        musicaArtista.setText(currentSong.getArtista());
        if (currentSong.getAlbum().equalsIgnoreCase(null))
            musicaArtista.setText("Album");
        musicaAlbum.setText(currentSong.getAlbum());
        tempoTotal.setText(converterEmMilisegundos(currentSong.getDuration()));
        pausePlay.setOnClickListener(v -> pausePlayMusica());
        nextBtn.setOnClickListener(v -> nextMusica());
        previousBtn.setOnClickListener(v -> previousMusica());
        playMusica();
    }

    public static String converterEmMilisegundos(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    private void playMusica() {

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextMusica() {

        if (MediaPlayerModel.getCurrentIndex() == musicasList.size() - 1)
            return;
        MediaPlayerModel.setCurrentIndex(MediaPlayerModel.getCurrentIndex() + 1);
        mediaPlayer.reset();
        setMusicarPlayer();

    }

    private void previousMusica() {
        if (MediaPlayerModel.getCurrentIndex() == 0)
            return;
        MediaPlayerModel.setCurrentIndex(MediaPlayerModel.getCurrentIndex() - 1);
        mediaPlayer.reset();
        setMusicarPlayer();
    }

    private void pausePlayMusica() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }
}