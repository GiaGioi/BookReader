package com.gioidev.book.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.Constants;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;
import com.gioidev.book.Fragment.FakePageFragment;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.blogc.android.views.ExpandableTextView;
import es.dmoral.toasty.Toasty;

public class ReadBookActivity extends AppCompatActivity implements View.OnClickListener
        , OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    HorizontalModel horizontalModel;
    private ImageView imageX;
    private ImageView imageShare;
    private ImageView imageBg;
    private ImageView image;
    private TextView tvTenSach;
    private TextView tvTenTacGia;
    private TextView tvGia;
    private TextView tvPrice;
    private ExpandableTextView expandableTextView;
    private TextView tvToggle;
    private Button btnReadBook;
    private ImageView imageForward;
    private TextView tvCategory;
    FolioReader folioReader;
    String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setOnClosedListener(this);

        getHighlightsAndSave();
        expandtext();
        init();


    }

    public void expandtext() {
        final ExpandableTextView expandableTextView = (ExpandableTextView) this.findViewById(R.id.expandableTextView);
        final TextView buttonToggle = (TextView) this.findViewById(R.id.tv_toggle);

// set animation duration via code, but preferable in your layout files by using the animation_duration attribute
        expandableTextView.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        expandableTextView.setInterpolator(new OvershootInterpolator());

// or set them separately
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

// toggle the ExpandableTextView
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                buttonToggle.setText(expandableTextView.isExpanded() ? R.string.expand : R.string.collapse);
                expandableTextView.toggle();
            }
        });

// but, you can also do the checks yourself
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (expandableTextView.isExpanded()) {
                    expandableTextView.collapse();
                    buttonToggle.setText(R.string.expand);
                } else {
                    expandableTextView.expand();
                    buttonToggle.setText(R.string.collapse);
                }
            }
        });

// listen for expand / collapse events
        expandableTextView.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(final ExpandableTextView view) {
                Log.d("TAG", "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view) {
                Log.d("TAG", "ExpandableTextView collapsed");
            }
        });
    }

    public void init() {
        imageX = findViewById(R.id.image_x);
        imageShare = findViewById(R.id.image_share);
        imageBg = findViewById(R.id.image_bg);
        image = findViewById(R.id.image);
        tvTenSach = findViewById(R.id.tvTenSach);
        tvTenTacGia = findViewById(R.id.tvTenTacGia);
        tvGia = findViewById(R.id.tvGia);
        tvPrice = findViewById(R.id.tvPrice);
        expandableTextView = findViewById(R.id.expandableTextView);
        tvToggle = findViewById(R.id.tv_toggle);
        btnReadBook = findViewById(R.id.btnReadBook);
        imageForward = findViewById(R.id.image_forward);
        tvCategory = findViewById(R.id.tvCategory);

        btnReadBook.setOnClickListener(this);
        imageX.setOnClickListener(this);

        Intent intent = getIntent();
        String namebook = intent.getStringExtra("NameBook");
        String nameauthor = intent.getStringExtra("NameAuthor");
        String url = intent.getStringExtra("Url");
        String description = intent.getStringExtra("Description");
        String gs = intent.getStringExtra("Gs");
        String imagebg = intent.getStringExtra("Image");
        String price = intent.getStringExtra("Price");
        String category = intent.getStringExtra("Category");

        Glide.with(this).load(imagebg).into(imageBg);
        Glide.with(this).load(imagebg).into(image);
        tvTenSach.setText(namebook);
        tvTenTacGia.setText(nameauthor);
        expandableTextView.setText(description);
        tvPrice.setText(price);
        tvCategory.setText(category);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReadBook:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(ReadBookActivity.this, OpenBookActivity.class);
                        intent.putExtra("FileName", String.valueOf(filename));
                        startActivity(intent);
                    }
                }, 3000);

                download();
                break;
            case R.id.image_x:

                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void download() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");

        String storagePath = Environment.getExternalStorageDirectory()
                .getPath()
                + "/Book Reader/";
        File f = new File(storagePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        //storagePath.mkdirs();
//        String pathname = uri.toString();
        if (!f.exists()) {
            f.mkdirs();
        }
        Uri uri = Uri.parse(url);


        DownloadManager dm = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle("Book Reader")// Title of the Download Notification
                // Description of the Download Notification
                // Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(f))// Uri of the destination file
                .setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network

//                Log.d("Storage ",""+pathname);
        request.setDestinationInExternalPublicDir("/Book Reader", uri.getLastPathSegment());
        dm.enqueue(request);

//        File file = Environment.getDataDirectory();
//        String pathname = String.valueOf(Environment.getDataDirectory());

        File sdCardRoot = new File(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());

        filename = sdCardRoot.getPath();


        Log.e("check_path", "" + filename);
    }
//        Intent intent = getIntent();
//        String  url = intent.getStringExtra("Url");
//
//        String DIR_NAME = "Book Reader";
//
//        String downloadUrlOfImage = url;
//        File direct =
//                new File(Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                        .getAbsolutePath() + "/" + DIR_NAME);
//
//        String filename = direct + "/filename.epub";
//
//
//        if (!direct.exists()) {
//            direct.mkdir();
//            Log.d("TAG", "dir created for first time");
//        }
//        DownloadManager dm = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri downloadUri = Uri.parse(downloadUrlOfImage);
//        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
//                .setAllowedOverRoaming(false)
//                .setTitle(filename)
//                .setMimeType("file/epub")
//                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
//                        File.separator + DIR_NAME + File.separator + filename);
//
//        dm.enqueue(request);
//
//        Log.e("LOG_TAG", "download: " + filename);

    @Override
    public void onFolioReaderClosed() {
        Log.v(LOG_TAG, "-> onFolioReaderClosed");
    }

    private ReadPosition getLastReadPosition() {

        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
        return ReadPositionImpl.createInstance(jsonString);
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

    @Override
    public void saveReadPosition(ReadPosition readPosition) {

        Log.i(LOG_TAG, "-> ReadPosition = " + readPosition.toJson());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
}
