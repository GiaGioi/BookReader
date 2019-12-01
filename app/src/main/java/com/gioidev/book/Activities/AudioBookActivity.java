package com.gioidev.book.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.gioidev.book.Adapter.AdapterAudioBook.AdapterAudio;
import com.gioidev.book.Model.DownSong;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.SliderModel;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioBookActivity extends AppCompatActivity implements JcPlayerManagerListener {
    private Button btntest;
    RecyclerView mRecyclerView;
    ArrayList<DownSong> downSongArrayList = new ArrayList<>();
    AdapterAudio myAdapter;
    DatabaseReference database;
    private StorageReference mStorageRef;
    private JcPlayerView jcplayer;
    private LinearLayout cardviewsong;
    private ImageView btnplaymusic;



    MediaPlayer mediaPlayer;
    private TextView namesong;

    String Chapter;
    String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book);
//        setUpRV();


        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.songcardview,null);


        namesong = (TextView) view.findViewById(R.id.namesong);
        mRecyclerView= findViewById(R.id.recyclesong);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterAudio(this,downSongArrayList);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        getDataHorizontal();







    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(@NotNull JcStatus jcStatus) {

    }

    @Override
    public void onJcpError(@NotNull Throwable throwable) {

    }

    @Override
    public void onPaused(@NotNull JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(@NotNull JcStatus jcStatus) {


    }

    @Override
    public void onPreparedAudio(@NotNull JcStatus jcStatus) {

    }

    @Override
    public void onStopped(@NotNull JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(@NotNull JcStatus jcStatus) {

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
    @Override
    protected void onStop() {
        super.onStop();
        jcplayer.createNotification();
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
