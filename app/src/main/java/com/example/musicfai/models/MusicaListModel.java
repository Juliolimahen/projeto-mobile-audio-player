package com.example.musicfai.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicfai.views.PlayerActivity;
import com.example.musicfai.R;

public class MusicaListModel extends RecyclerView.Adapter<MusicaListModel.ViewHolder> {

    private ArrayList<MusicaModel> listaDeMusicas;
    private transient Context context;

    public MusicaListModel(ArrayList<MusicaModel> listaDeMusicas, Context context) {
        this.listaDeMusicas = listaDeMusicas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.musica_lista, parent, false);
        return new MusicaListModel.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicaListModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MusicaModel songData = listaDeMusicas.get(position);
        holder.titleTextView.setText(songData.getTitle());

        if (MediaPlayerModel.getCurrentIndex() == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#00FFFF"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        }

        Uri artworkUri = songData.getArtworkUri();

        if (artworkUri !=null ){
            holder.iconImageView.setImageURI(artworkUri);

           if (holder.iconImageView.getDrawable()== null){
                holder.iconImageView.setImageResource(R.drawable.sound_button);
           }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerModel.getInstance().reset();
                MediaPlayerModel.setCurrentIndex(position);
                Intent intent = new Intent(context, PlayerActivity.class);
                Intent list = intent.putExtra("LIST", listaDeMusicas);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeMusicas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }

    private byte[] getAlbum() {
        return getAlbum();
    }

    private byte[] getAlbum(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}