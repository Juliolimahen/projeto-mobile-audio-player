package com.example.musicfai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicfai.models.MusicaModel;
import com.example.musicfai.models.MusicaListModel;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listaDeMusicas;
    private ArrayList<MusicaModel> musicasList = new ArrayList<>();
    private TextView ListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeMusicas = findViewById(R.id.recycler_view);
        ListaVazia = findViewById(R.id.no_songs_text);

        if (checarPermissao() == false) {
            solicitarPermissao();
            return;
        } else {
            bootstrap();
        }
    }

    public void bootstrap() {

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            MusicaModel musicaData = new MusicaModel(cursor.getString(1),cursor.getString(0),cursor.getString(2), cursor.getString(3),cursor.getString(4));
            if(new File(musicaData.getPath()).exists())
                musicasList.add(musicaData);
        }

        if(musicasList.size()==0){
            ListaVazia.setVisibility(View.VISIBLE);
        }else{
            //recyclerview
            listaDeMusicas.setLayoutManager(new LinearLayoutManager(this));
            listaDeMusicas.setAdapter(new MusicaListModel(musicasList,getApplicationContext()));
        }
    }

    private void solicitarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "É necessario liberar a permissão de armzenamento. Por favar faça isso nas configurações!", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }

   private boolean checarPermissao() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listaDeMusicas != null) {
            listaDeMusicas.setAdapter(new MusicaListModel(musicasList, getApplicationContext()));
        }
    }
}