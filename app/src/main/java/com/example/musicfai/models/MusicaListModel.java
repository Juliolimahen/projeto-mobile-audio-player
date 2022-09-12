package com.example.musicfai.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musicfai.PlayerActivity;
import com.example.musicfai.R;

public class MusicaListModel extends RecyclerView.Adapter<MusicaListModel.ViewHolder> {

    private ArrayList<MusicaModel> songsList;
    private Context context;

    public MusicaListModel(ArrayList<MusicaModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.musica_lista, parent, false);
        return new MusicaListModel.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicaListModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MusicaModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());

        if (MediaPlayerModel.getCurrentIndex() == position) {
            holder.titleTextView.setTextColor(Color.parseColor("#00FFFF"));
        } else {
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerModel.getInstance().reset();
                MediaPlayerModel.setCurrentIndex(position);
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("LIST", songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
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
