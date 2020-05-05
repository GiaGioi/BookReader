package com.gioidev.wakabook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.OnHighlightListener;
import com.gioidev.wakabook.Adapter.AdapterBook.BookViewAdapter;

import com.gioidev.wakabook.Model.Books;
import com.gioidev.book.R;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class DownloadActivity extends
        AppCompatActivity implements OnHighlightListener{

    private TextView tvDownload;
    private Button btnDownload;

    DatabaseReference mDatabase;
    private RecyclerView rvDownload;
    BookViewAdapter mAdapter;
    List<Books> books;
    private ImageView imageClick;
    private TextView tvtacGia;
    private TextView tvAuther;
    FolioReader folioReader;

    AssetManager assetManager;
    PDFView pdfView;
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        folioReader = FolioReader.get()
                .setOnHighlightListener(this);

        imageClick = findViewById(R.id.imageClick);
        tvtacGia = findViewById(R.id.tvTenSach);
        tvAuther = findViewById(R.id.tvAuther);
        btnDownload = findViewById(R.id.btnDownload);

        getHighlightsAndSave();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });


    }


    public void download() {

        String DIR_NAME = "Book Reader";

        String downloadUrlOfImage = "https://firebasestorage.googleapis.com/v0/b/book-38ccb.appspot.com/o/Marketing%20-%20Kh%C3%A1ch%20h%C3%A0ng%2FEpub%2F40%2B%20B%C3%AD%20K%C3%ADp%20Chinh%20Ph%E1%BB%A5c%20Kh%C3%A1ch%20H%C3%A0ng%20Qua%20%C4%90i%E1%BB%87n%20Tho%E1%BA%A1i.epub?alt=media&token=78ea6462-147e-4cbf-9a7f-d75cb792a521";
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + DIR_NAME);

        String filename = direct + "/filename.epub";
        Intent intent = new Intent(DownloadActivity.this,ReadBookActivity.class);
        intent.putExtra("FileName", filename);
        folioReader.openBook(filename);

        if (!direct.exists()) {
            direct.mkdir();
            Log.d("TAG", "dir created for first time");
        }
        DownloadManager dm = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(downloadUrlOfImage);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("file/epub")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + DIR_NAME + File.separator + filename);

        dm.enqueue(request);

        Log.e("LOG_TAG", "download: " + filename);
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
    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HighlightData> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(null, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }
    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

    }
}
