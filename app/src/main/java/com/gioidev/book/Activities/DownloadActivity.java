package com.gioidev.book.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.BufferedReader;
import java.io.File;
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
        AppCompatActivity {

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
    PDFView pdfView;
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
//
//        imageClick = findViewById(R.id.imageClick);
//        tvtacGia = findViewById(R.id.tvTenSach);
//        tvAuther = findViewById(R.id.tvAuther);
//        btnDownload = findViewById(R.id.btnDownload);
//
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                download();
//            }
//        });


    }


    public void download() {

        String DIR_NAME = "Download";
        String filename = "filename.epub";
        String downloadUrlOfImage = "https://firebasestorage.googleapis.com/v0/b/book-38ccb.appspot.com/o/S%C3%A1ch%20t%C3%A2m%20l%C3%AD%20-%20K%C4%A9%20n%C4%83ng%20s%E1%BB%91ng%2FPDF%2FBi%E1%BA%BFt%20H%C3%A0i%20L%C3%B2ng.pdf?alt=media&token=1bccc5d3-0689-4aca-9ab9-9a27a92f686e";
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + DIR_NAME + "/");


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
    }


}
