package com.gioidev.book.Adapter.AdapterAudioBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.gioidev.book.Activities.AudioBookActivity;
import com.gioidev.book.Holder.SongHolder;
import com.gioidev.book.Model.DownSong;
import com.gioidev.book.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAudio extends RecyclerView.Adapter<SongHolder> {


    Context context;
    ArrayList<DownSong> downSongs;
    private OnlistSongListener monlistSongListener;

//    public AdapterAudio(Context context, ArrayList<DownSong> downSongs,OnlistSongListener onlistSongListener ) {
//        this.context = context;
//        this.downSongs = downSongs;
//        this.onlistSongListener = OnlistSongListener;
//    }

    public AdapterAudio(Context context, ArrayList<DownSong> downSongs, OnlistSongListener onlistSongListener) {
        this.context = context;
        this.downSongs = downSongs;
        this.monlistSongListener = onlistSongListener;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songcardview, parent, false);
        return new SongHolder(view,monlistSongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder songHolder, int position) {
        DownSong downSong = downSongs.get(position);

        songHolder.mName.setText(downSong.getName());
//        songHolder.mLink.setText(downSong.getLink());
//        songHolder.mTime.setText(downSong.getTime());


    }

    @Override
    public int getItemCount() {
        return  downSongs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mName;
        OnlistSongListener onlistSongListener;
        public ViewHolder(@NonNull View itemView, OnlistSongListener onlistSongListener) {
            super(itemView);
            mName = itemView.findViewById(R.id.namesong);
            this.onlistSongListener = onlistSongListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onlistSongListener.onSongClick(getAdapterPosition());
        }
    }
    public interface OnlistSongListener{
        void onSongClick(int position);
    }
}
