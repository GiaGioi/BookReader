package com.gioidev.wakabook.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.book.R;

public class SongHolder extends RecyclerView.ViewHolder {

public TextView mName;
public TextView mLink;
public TextView mTime;
public Button mStopSong;
public Button mPlaySong;
    public SongHolder(@NonNull View itemView) {
        super(itemView);
        mName=itemView.findViewById(R.id.namesong);
//        mLink=itemView.findViewById(R.id.linksong);
//        mTime=itemView.findViewById(R.id.timesong);
    }
}
