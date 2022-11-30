package com.example.musicfai.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicfai.R;
import com.example.musicfai.models.MusicaModel;
import com.example.musicfai.models.MusicaListModel;
import com.example.musicfai.services.IPermissao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IPermissao {

    private RecyclerView listaDeMusicas;
    private final ArrayList<MusicaModel> musicasList = new ArrayList<>();
    private TextView ListaVazia;
    private MusicaListModel musicaListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarCompenentes();

        if (checarPermissao() == false) {
            solicitarPermissao();
        } else {
            bootstrap();
        }
    }

    private void inicializarCompenentes() {
        listaDeMusicas = findViewById(R.id.recycler_view);
        ListaVazia = findViewById(R.id.sem_musica_txt);
    }

    private void bootstrap() {

        List<MusicaModel> musicas;
        musicas = new ArrayList<>();

        String[] projection;
        projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        //cache curso indices
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
        int artistaColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        int AlbumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        int AlbumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

        while (cursor.moveToNext()) {
            String path = cursor.getString(pathColumn);
            long id = cursor.getLong(idColumn);
            String titulo = cursor.getString(nameColumn);
            String duration = cursor.getString(durationColumn);
            String artista = cursor.getString(artistaColumn);
            String album = cursor.getString(AlbumColumn);
            long albumId = cursor.getLong(AlbumIdColumn);

            //Song Uri
            Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

            //Album artwork Uri
            Uri albumArtworkUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

            MusicaModel musica = new MusicaModel(path,
                    titulo,
                    duration,
                    artista,
                    album,
                    uri,
                    albumArtworkUri);

            if (new File(musica.getPath()).exists())
                musicas.add(musica);
        }
        musicasList.addAll(musicas);
        if (musicasList.size() == 0) ListaVazia.setVisibility(View.VISIBLE);
        else {
            listaDeMusicas.setLayoutManager(new LinearLayoutManager(this));
            listaDeMusicas.setAdapter(new MusicaListModel(musicasList, getApplicationContext()));
        }
    }

    public void solicitarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "É necessario liberar a permissão de armzenamento. " +
                    "Por favor faça isso nas configurações!", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    public boolean checarPermissao() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listaDeMusicas != null) {
            listaDeMusicas.setAdapter(new MusicaListModel(musicasList, getApplicationContext()));
        }
    }
}