package com.gioidev.book.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gioidev.book.R;

import at.blogc.android.views.ExpandableTextView;

public class ReadBookComicActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rvtoolbar;
    private ImageView imageX;
    private ImageView imageShare;
    private ImageView imageBg;
    private ImageView image;
    private TextView tvTenSach;
    private TextView tvTenTacGia;
    private TextView tvGia;
    private TextView tvPrice;
    private RecyclerView rvReadBookComic;
    private ExpandableTextView expandableTextView;
    private TextView tvToggle;
    private TextView tvCategory;
    private ImageView imageForward;
    private LinearLayout linerFanpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book_comic);
    }
    public void init(){
        rvtoolbar = findViewById(R.id.rvtoolbar);
        imageX = findViewById(R.id.image_x);
        imageShare = findViewById(R.id.image_share);
        imageBg = findViewById(R.id.image_bg);
        image = findViewById(R.id.image);
        tvTenSach = findViewById(R.id.tvTenSach);
        tvTenTacGia = findViewById(R.id.tvTenTacGia);
        tvGia = findViewById(R.id.tvGia);
        tvPrice = findViewById(R.id.tvPrice);
        rvReadBookComic = findViewById(R.id.rv_read_book_comic);
        expandableTextView = findViewById(R.id.expandableTextView);
        tvToggle = findViewById(R.id.tv_toggle);
        tvCategory = findViewById(R.id.tvCategory);
        imageForward = findViewById(R.id.image_forward);
        linerFanpage = findViewById(R.id.linerFanpage);


    }

    @Override
    public void onClick(View view) {

    }
}
