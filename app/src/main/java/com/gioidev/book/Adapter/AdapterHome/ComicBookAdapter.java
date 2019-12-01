package com.gioidev.book.Adapter.AdapterHome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gioidev.book.Activities.ReadBookActivity;
import com.gioidev.book.Activities.ReadBookComicActivity;
import com.gioidev.book.Model.ComicBookModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class ComicBookAdapter extends PagerAdapter {

    private ArrayList<ComicBookModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public ComicBookAdapter(ArrayList<ComicBookModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);

        ComicBookModel model = models.get(position);
        View view = layoutInflater.inflate(R.layout.item_comic_book, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        Glide.with(context).load(model.getImage()).into(imageView);
        title.setText(model.getNameBook());
        desc.setText(model.getNameAuthor());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                SharedPreferences preferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
                preferences.edit().putString("Horizontal","4").apply();

                Intent intent = new Intent(context,ReadBookActivity.class);
                intent.putExtra("NameBook", model.getNameBook());
                intent.putExtra("NameAuthor", model.getNameAuthor());
                intent.putExtra("Url", model.getUrl());
                intent.putExtra("Description", model.getDescription());
                intent.putExtra("Gs", model.getGs());
                intent.putExtra("Image", model.getImage());
                intent.putExtra("Price", model.getPrice());
                intent.putExtra("Category", model.getCategory());
                context.startActivity(intent);

            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
