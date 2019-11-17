package com.gioidev.book.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;
import com.gioidev.book.Adapter.AdapterBook.BookViewAdapter;

import com.gioidev.book.Model.Books;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.User;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class DownloadActivity extends
        AppCompatActivity implements OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener {

    private TextView tvDownload;
    private Button btnDownload;

    DatabaseReference mDatabase;
    private RecyclerView rvDownload;
    BookViewAdapter mAdapter;
    List<Books> books;
    private ImageView imageClick;
    private TextView tvtacGia;
    private TextView tvAuther;
    FolioReader reader;

    AssetManager assetManager;

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        assetManager = getAssets();
        reader = FolioReader.get()
                .setOnHighlightListener(this)
                .setReadPositionListener(this)
                .setOnClosedListener(this);

        imageClick = findViewById(R.id.imageClick);
        tvtacGia = findViewById(R.id.tvTenSach);
        tvAuther = findViewById(R.id.tvAuther);
        btnDownload = findViewById(R.id.btnDownload);

//        rvDownload = findViewById(R.id.rvDownload);
//        tvDownload = findViewById(R.id.tvDownload);

//        rvDownload.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mAdapter = new BookViewAdapter(this, books);
//        rvDownload.setAdapter(mAdapter);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                download();
                try {
                    // find InputStream for book
                    InputStream epubInputStream = assetManager

                            .open("gs://book-38ccb.appspot.com/Marketing - Khách hàng/Epub/40+ Bí Kíp Chinh Phục Khách Hàng Qua Điện Thoại.epub");

                    // Load Book from inputStream
                    Book book = (new EpubReader()).readEpub(epubInputStream);

                    // Log the book's authors
                    Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

                    // Log the book's title
                    Log.i("epublib", "title: " + book.getTitle());

                    // Log the book's coverimage property
                    Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
                            .getInputStream());
                    Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
                            + coverImage.getHeight() + " pixels");

                    // Log the tale of contents
                    logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
                } catch (IOException e) {
                    Log.e("epublib", e.getMessage());
                }

            }
        });

    }
    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            Log.i("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }
    }
    private ReadPosition getLastReadPosition() {

        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
        return ReadPositionImpl.createInstance(jsonString);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    public void download(){
        books = new ArrayList<>();
        mDatabase.child("books/PDF/SkillBook/Url")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = (String) dataSnapshot.getValue();

                            ReadPosition readPosition = getLastReadPosition();

                            Config config = AppUtil.getSavedConfig(getApplicationContext());
                            if (config == null)
                                config = new Config();
                            config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);
                            reader.setReadPosition(readPosition)
                                    .setConfig(config, true)
                                    .openBook(name);

                            Log.e("Get Data", "" + reader);
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }



    @Override
    public void saveReadPosition(ReadPosition readPosition) {
        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());
    }
    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               List<HighlightData> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            Objects.requireNonNull(loadAssetTextAsString("highlights/highlights_data.json")),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    reader.saveReceivedHighLights(null, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }

    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
        Toast.makeText(this,
                "highlight id = " + highlight.getUUID() + " type = " + type,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onFolioReaderClosed() {
        Log.v(LOG_TAG, "-> onFolioReaderClosed");
    }
}
