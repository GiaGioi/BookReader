package com.gioidev.wakabook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gioidev.book.R;
import com.pdftron.pdf.controls.DocumentActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import at.blogc.android.views.ExpandableTextView;

public class ReadBookComicActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String TAG = "ReadBookComic";
    private RelativeLayout rvtoolbar;
    private ImageView imageX;
    private ImageView imageShare;
    private ImageView imageBg;
    private ImageView image;
    private TextView tvTenSach;
    private TextView tvTenTacGia;
    private TextView tvGia;
    private TextView tvPrice;
    private ExpandableTextView expandableTextView;
    private Button button;
    private TextView tvToggle;
    private TextView tvCategory;
    private ImageView imageForward;
    private LinearLayout linerFanpage;

    Uri uri;
    Dialog dialog;
    private RelativeLayout rvLayout;
    private ImageView image2;
    private ImageView view1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book_comic);

        expandtext();
        init();
    }

    public void init() {
        rvtoolbar = findViewById(R.id.rvtoolbar);
        imageX = findViewById(R.id.image_x);
        imageShare = findViewById(R.id.image_share);
        imageBg = findViewById(R.id.image_bg);
        image = findViewById(R.id.image);
        tvTenSach = findViewById(R.id.tvTenSachComic);
        tvTenTacGia = findViewById(R.id.tvTenTacGia);
        tvGia = findViewById(R.id.tvGia);
        button = findViewById(R.id.btnReadBook);
        tvPrice = findViewById(R.id.tvPrice);
        expandableTextView = findViewById(R.id.expandableTextView);
        tvToggle = findViewById(R.id.tv_toggle);
        tvCategory = findViewById(R.id.tvCategory);
        imageForward = findViewById(R.id.image_forward);
        linerFanpage = findViewById(R.id.linerFanpage);


        button.setOnClickListener(this);
        imageX.setOnClickListener(this);
        imageShare.setOnClickListener(this);

        Intent intent = getIntent();
        String namebook = intent.getStringExtra("NameBook");
        String nameauthor = intent.getStringExtra("NameAuthor");
        String description = intent.getStringExtra("Description");
        String gs = intent.getStringExtra("Gs");
        String imagebg = intent.getStringExtra("Image");
        String price = intent.getStringExtra("Price");
        String category = intent.getStringExtra("Category");

        Log.e(TAG, "init: " + description);

        Glide.with(this).load(imagebg).into(imageBg);
        Glide.with(this).load(imagebg).into(image);
        tvTenSach.setText(namebook);
        tvTenTacGia.setText(nameauthor);
        expandableTextView.setText(description);
        tvPrice.setText(price);
        tvCategory.setText(category);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReadBook:
                new DownloadfilePDF().execute();

                break;
            case R.id.image_x:
                startActivity(new Intent(ReadBookComicActivity.this, HomeActivity.class));
                break;
            case R.id.image_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Mời bạn đọc sách");
                startActivity(intent);
                break;
        }
    }

    public void viewDialog() {
        Intent intent = getIntent();
        String imagebg = intent.getStringExtra("Image");

        View view = getLayoutInflater().inflate(R.layout.item_full_screen_dialog, null);
        rvLayout = view.findViewById(R.id.rvLayout);
        view1 = view.findViewById(R.id.viewImage);
        progressBar = view.findViewById(R.id.progress);

        image2 = view.findViewById(R.id.image);

        Glide.with(this).load(imagebg).into(image2);
        Glide.with(this).load(imagebg).into(view1);

        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(view);

        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        dialog.show();
    }
    class DownloadfilePDF extends AsyncTask<String, String, String> {

        // Show Progress bar before downloading Music
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            viewDialog();
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                Intent intent = getIntent();
                String url1 = intent.getStringExtra("url");
                uri = Uri.parse(url1);

                URL url = new URL(url1);
                URLConnection conection = url.openConnection();
                conection.connect();
                // Get File file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                // Output stream to write file in SD card
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress which triggers onProgressUpdate method
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // Write data to file
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();
                // Close streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        // While Downloading Music File
        protected void onProgressUpdate(String... progress) {
            // Set progress percentage

            progressBar.setProgress(Integer.parseInt(progress[0]));
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            // Dismiss the dialog after the Music file was downloaded
            // Play the music
            File sdCardRoot = new File(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());

            DocumentActivity.openDocument(ReadBookComicActivity.this, Uri.parse(sdCardRoot.getPath()));
            dialog.dismiss();
        }

    }
}
