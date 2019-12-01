package com.gioidev.book.Adapter.AdapterBook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.book.Activities.ReadBookComicActivity;
import com.gioidev.book.Model.ChildComicBookModel;
import com.gioidev.book.Model.ComicBookModel;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ChildComicBookAdapter extends
        RecyclerView.Adapter<ChildComicBookAdapter.ChildComicBookHolder> {

    private Context context;
    private ArrayList<ChildComicBookModel> bookModels;

    public ChildComicBookAdapter(Context context, ArrayList<ChildComicBookModel> bookModels) {
        this.context = context;
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public ChildComicBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_child_comic_book, parent, false);

        return new ChildComicBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildComicBookHolder holder, int position) {
        final ChildComicBookModel current = bookModels.get(position);

//        holder.tvChapter.setText(current.getName());

//        Intent intent = new Intent(context,ReadBookComicActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("NameBook", current.getUrl());
//        bundle.putString("NameAuthor", current.getName());
//        intent.putExtras(bundle);
//        context.startActivity(intent);

        holder.tvChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(context,"A" + current.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("TAG", "onBindViewHolder: " + current.getUrl());
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class ChildComicBookHolder extends RecyclerView.ViewHolder {
        private TextView tvChapter;

        public ChildComicBookHolder(@NonNull View itemView) {
            super(itemView);

            tvChapter = itemView.findViewById(R.id.tvChapter);

        }
    }
}
