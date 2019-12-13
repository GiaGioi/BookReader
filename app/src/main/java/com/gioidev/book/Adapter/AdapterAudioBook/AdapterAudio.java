package com.gioidev.book.Adapter.AdapterAudioBook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.gioidev.book.Activities.AudioBookActivity;
import com.gioidev.book.Activities.DangkiActivity;
import com.gioidev.book.Holder.SongHolder;
import com.gioidev.book.Model.DownSong;
import com.gioidev.book.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterAudio extends RecyclerView.Adapter<AdapterAudio.MyViewHolder> {

        Context context;
    List<DownSong> downSongs;
    public AdapterAudio(Context context, List<DownSong> downSongs) {
        this.context = context;
        this.downSongs = downSongs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songcardview, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.linearLayout.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click ",String.valueOf(holder.getAdapterPosition()));
                try {
                    ((AudioBookActivity)context).playsong(holder.getAdapterPosition());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mName.setText(downSongs.get(position).getName());
        holder.mTime.setText(downSongs.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return downSongs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mTime;
        private CardView linearLayout;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.cardviewsong);
        mName = itemView.findViewById(R.id.namesong);
        mTime = itemView.findViewById(R.id.timesong);
    }


    }

}
