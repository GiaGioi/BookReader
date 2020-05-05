package com.gioidev.wakabook.Adapter.AdapterBook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.wakabook.Activities.ReadBookActivity;
import com.gioidev.wakabook.Model.ComicBookModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class ComicBookFragmentAdapter extends
        RecyclerView.Adapter<ComicBookFragmentAdapter.ComicBookFragmentHolder> {

    private Context mContext;
    private ArrayList<ComicBookModel> mArrayList;

    public ComicBookFragmentAdapter(Context mContext,
                                    ArrayList<ComicBookModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public ComicBookFragmentAdapter.ComicBookFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hozizontal, parent, false);
        return new ComicBookFragmentAdapter.ComicBookFragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicBookFragmentHolder holder, int position) {
        final ComicBookModel current = mArrayList.get(position);
        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = mContext.getSharedPreferences("Data", Context.MODE_PRIVATE);
                preferences.edit().putString("Horizontal","1").apply();

                Intent intent = new Intent(mContext, ReadBookActivity.class);
                intent.putExtra("Url", String.valueOf(current.getUrl()));
                intent.putExtra("NameBook", current.getNameBook());
                intent.putExtra("NameAuthor", current.getNameAuthor());
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Gs", current.getGs());
                intent.putExtra("Image", current.getImage());
                intent.putExtra("Price", current.getPrice());
                intent.putExtra("Category", current.getCategory());
                mContext.startActivity(intent);

                Log.e("TAG", "onClick: " + current);
            }
        });
        Glide.with(mContext).load(current.getImage()).into(holder.imageClick);
        holder.tvAuther.setText(current.getNameAuthor());
        holder.tvSach.setText(current.getNameBook());
        holder.tvAuther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Image", "onClick: " + current.getNameAuthor());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ComicBookFragmentHolder extends RecyclerView.ViewHolder {

        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;
        private TextView tvSach;

        public ComicBookFragmentHolder(@NonNull View itemView) {
            super(itemView);

            tvSach = itemView.findViewById(R.id.tvNameBook);
            linerBook = itemView.findViewById(R.id.linerBook);
            imageClick = itemView.findViewById(R.id.imageClick);
            tvAuther = itemView.findViewById(R.id.tvAuther);
        }
    }
}
