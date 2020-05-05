package com.gioidev.wakabook.Fragment;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.wakabook.Adapter.AdapterAudioBook.AdapterAudio;
import com.gioidev.wakabook.Model.DownSong;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Audio_Book extends Fragment {
    View v;
    private TextView namesong;
    RecyclerView mRecyclerView;
    ArrayList<DownSong> downSongArrayList = new ArrayList<>();
    AdapterAudio myAdapter;
    DatabaseReference database;
    private StorageReference mStorageRef;
    MediaPlayer mediaPlayer;
    String Chapter;
    String Url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_audio_book,container,false);
        View view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.songcardview,null);
        namesong = (TextView) view.findViewById(R.id.namesong);
        mRecyclerView= (RecyclerView)v.findViewById(R.id.recyclesong);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter = new AdapterAudio(getContext(),downSongArrayList);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        getDataHorizontal();
        return v;
    }
    public void getDataHorizontal() {
        database = FirebaseDatabase.getInstance().getReference("audiobook").child("audio1");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                downSongArrayList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());

                    String nameBook = String.valueOf(snapshot.child("NameAudio").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("AuthorAudio").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    Url = String.valueOf(snapshot.child("Url").getValue());
                    String Talker = String.valueOf(snapshot.child("Talker").getValue());
                    String Time = String.valueOf(snapshot.child("Time").getValue());
                    Chapter = String.valueOf(snapshot.child("Chapter").getValue());

                    DownSong  downSong = new DownSong();
                    downSong.setName(nameBook);
                    downSong.setAuthorAudio(nameAuthor);
                    downSong.setDescription(Description);
                    downSong.setLink(Url);
                    downSong.setTalker(Talker);
                    downSong.setTime(Time);
                    downSong.setChapter(Chapter);

                    downSongArrayList.add(downSong);
                    mRecyclerView.setAdapter(myAdapter);


                    myAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void playsong(int adapterPosition) throws IOException {

        final DownSong downSong = downSongArrayList.get(adapterPosition);


        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(downSong.getLink());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.prepareAsync();

    }
}
