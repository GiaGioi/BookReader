package com.gioidev.book.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.gioidev.book.Fragment.Fragment_Ki_Nang;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.User;
import com.gioidev.book.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pdftron.pdf.controls.DocumentActivity;

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

    public final String TAG = ReadBookActivity.class.getSimpleName();
    private Button btnDialog;
    Dialog dialog;
    private RelativeLayout rvLayout;
    private ImageView image2;
    private ImageView view1;
    private ProgressBar progressBar;
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
    private TextView tvTenBook;
    long maxid = 0;
    String namebook2;
    String namebook;

    FirebaseDatabase database;
    DatabaseReference reference;
    FolioReader folioReader;
    String filename;
    Uri uri;
    SharedPreferences sharedPreferences;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setOnClosedListener(this);

        getHighlightsAndSave();
        user = new User();
        expandtext();
        init();
        getUID();

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

    @SuppressLint("NewApi")
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
        tvTenBook = findViewById(R.id.tvTenBook);

//        btnReadBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean vip = false;
//                if (getBaseContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){
//                    if (equals(vip)== true){
//                        Log.e("ok","dit con me");
//                    }
//
//                }
//            }
//        });
        btnReadBook.setOnClickListener(this);
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

        Glide.with(this).load(imagebg).into(imageBg);
        Glide.with(this).load(imagebg).into(image);
        tvTenSach.setText(namebook);
        tvTenTacGia.setText(nameauthor);
        expandableTextView.setText(description);
        tvPrice.setText(price);
        tvCategory.setText(category);
        tvTenBook.setText(namebook);

//        sharedPreferences = getSharedPreferences("DATA2",MODE_PRIVATE);
//        sharedPreferences.edit().putString("Image",imagebg).apply();

    }

    private void getUID() {
        Intent intent = getIntent();
        namebook = intent.getStringExtra("NameBook");
        reference = FirebaseDatabase.getInstance().getReference("user").child("PDF");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());
                    namebook2 = String.valueOf(snapshot.child("nameBook").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e(TAG, "getUID: " + namebook);
        Log.e(TAG, "getUID2: " + namebook2);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReadBook:
                if (getApplication().getSharedPreferences("Data", Context.MODE_PRIVATE).getString("Horizontal", "").equals("0")) {

                    new DownloadfromInternet().execute();
                }
                if (getApplication().getSharedPreferences("Data", Context.MODE_PRIVATE).getString("Horizontal", "").equals("1")) {
                    new DownloadfilePDF().execute();

                }
                if (getApplication().getSharedPreferences("Data", Context.MODE_PRIVATE).getString("Horizontal", "").equals("3")) {
                    new DownloadfilePDF().execute();
                }
                if (getApplication().getSharedPreferences("Data", Context.MODE_PRIVATE).getString("Horizontal", "").equals("4")) {
                    new DownloadfilePDF().execute();
                }
                break;
            case R.id.image_x:
                startActivity(new Intent(ReadBookActivity.this, HomeActivity.class));
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

    @RequiresApi(api = Build.VERSION_CODES.N)

    private void getData() {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null) {
            String userID = user1.getUid();
            user.setUid(userID);
        }
        Intent intent = getIntent();
        String namebook = intent.getStringExtra("NameBook");
        String nameauthor = intent.getStringExtra("NameAuthor");
        String description = intent.getStringExtra("Description");
        String gs = intent.getStringExtra("Gs");
        String imagebg = intent.getStringExtra("Image");
        String price = intent.getStringExtra("Price");
        String category = intent.getStringExtra("Category");
        String url = intent.getStringExtra("Url");

        user.setNameBook(namebook);
        user.setNameAuthor(nameauthor);
        user.setDescription(description);
        user.setGs(gs);
        user.setImage(imagebg);
        user.setPrice(price);
        user.setCategory(category);
        user.setUrl(url);

        Log.e(TAG, "getData: " + url);

    }


    class DownloadfromInternet extends AsyncTask<String, String, String> {

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
                String url1 = intent.getStringExtra("Url");
                Uri uri = Uri.parse(url1);

                URL url = new URL(url1);
                URLConnection conection = url.openConnection();
                conection.connect();
                // Get File file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                // Output stream to write file in SD card
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());

                File sdCardRoot = new File(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());
                filename = sdCardRoot.getPath();

                Log.e("TAG", "onCreate: " + filename);
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

            sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
            sharedPreferences.edit().putString("File", filename).apply();
            startActivity(new Intent(ReadBookActivity.this, OpenBookActivity.class));
            dialog.dismiss();
            // Play the music
        }
    }

    class DownloadfilePDF extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            viewDialog();
            if (!namebook.equalsIgnoreCase(namebook2)) {
                pushdata();
            } else {
                Log.e(TAG, "getUID: ");
            }
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                Intent intent = getIntent();
                String url1 = intent.getStringExtra("Url");
                uri = Uri.parse(url1);

                Log.e("TAG", "doInBackground: " + url1);
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

        protected void onProgressUpdate(String... progress) {
            // Set progress percentage

            progressBar.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            File sdCardRoot = new File(Environment.getExternalStorageDirectory().getPath() + "/Book Reader/" + uri.getLastPathSegment());

            DocumentActivity.openDocument(ReadBookActivity.this, Uri.parse(sdCardRoot.getPath()));
            dialog.dismiss();
        }
    }

    @SuppressLint("NewApi")
    public void pushdata() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user").child("PDF");
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        String getUid = user1.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount() + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getData();
//        reference.child(getUid).child(String.valueOf(maxid)).setValue(user);
        reference.push().setValue(user);

    }

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
