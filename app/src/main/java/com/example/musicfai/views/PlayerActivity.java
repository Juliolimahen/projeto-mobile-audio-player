package com.example.musicfai.views;

import static com.example.musicfai.models.MediaPlayerModel.*;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicfai.R;
import com.example.musicfai.models.MusicaModel;
import com.example.musicfai.services.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private TextView titulo;
    private TextView tempoAtual;
    private TextView tempoTotal;
    private TextView musicaArtista;
    private TextView musicaAlbum;
    private SeekBar seekBar;
    private ImageView pausePlay;
    private ImageView nextBtn;
    private ImageView previousBtn;
    private ImageView musicaIco;
    private ArrayList<MusicaModel> musicasList;
    private MusicaModel musicaAtual;
    private MediaPlayer mediaPlayer = getInstance();
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        inicializarComponetes();
        titulo.setSelected(true);
        musicasList = (ArrayList<MusicaModel>) getIntent().getSerializableExtra("LIST");

        inicializarMusicarPlayer();

        mediaPlayer.setOnCompletionListener(this);
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tempoAtual.setText((Utils.Milisegundos.converterEmMilisegundos(mediaPlayer.getCurrentPosition() + "")));

                    if (mediaPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.ic_pause);
                        musicaIco.setRotation(x++);
                    } else {
                        pausePlay.setImageResource(R.drawable.ic_play);
                        int j = (int) musicaIco.getRotation();
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

    private void inicializarComponetes() {
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
    }

    private void inicializarMusicarPlayer() {
        musicaAtual = musicasList.get(getCurrentIndex());
        titulo.setText(musicaAtual.getTitle());
        if (musicaAtual.getArtista().isEmpty())
            musicaArtista.setText("Artista");
        musicaArtista.setText(musicaAtual.getArtista());
        if (musicaAtual.getAlbum().isEmpty())
            musicaArtista.setText("Album");
        musicaAlbum.setText(musicaAtual.getAlbum());
        tempoTotal.setText(Utils.Milisegundos.converterEmMilisegundos((musicaAtual.getDuration())));
        pausePlay.setOnClickListener(v -> pausePlayMusica());
        nextBtn.setOnClickListener(v -> nextMusica());
        previousBtn.setOnClickListener(v -> previousMusica());

        Uri artworkUri = Uri.parse(musicaAtual.getArtworkUristr().toString());

        if (artworkUri != null) {
            musicaIco.setImageURI(artworkUri);

            if (musicaIco.getDrawable() == null) {
                musicaIco.setImageResource(R.drawable.sound_button);
            }
        }
        playMusica();
    }

    private void playMusica() {

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicaAtual.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextMusica() {

        if (getCurrentIndex() == musicasList.size() - 1)
            return;
        setCurrentIndex(getCurrentIndex() + 1);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.reset();
        inicializarMusicarPlayer();
    }

    private void previousMusica() {
        if (getCurrentIndex() == 0) {
            return;
        }
        setCurrentIndex(getCurrentIndex() - 1);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.reset();
        inicializarMusicarPlayer();
    }

    private void pausePlayMusica() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pausePlay.setImageResource(R.drawable.ic_play);
            mediaPlayer.setOnCompletionListener(this);
        } else {
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextMusica();
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }
        setCurrentIndex(getCurrentIndex() - 1);
    }
}
