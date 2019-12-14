package com.gioidev.book.Adapter.AdapterBook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.gioidev.book.Activities.ReadBookActivity;
import com.gioidev.book.Fragment.BookcaseFragment;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.Model.User;
import com.gioidev.book.R;

import java.util.ArrayList;

public class FragmentKiNangAdapter extends
        RecyclerView.Adapter<FragmentKiNangAdapter.FragmentKiNangHolder> {

    private Context mContext;
    private ArrayList<User> gridViewModels;

    public FragmentKiNangAdapter(Context mContext, ArrayList<User> gridViewModels) {
        this.mContext = mContext;
        this.gridViewModels = gridViewModels;
    }
    @NonNull
    @Override
    public FragmentKiNangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_ki_nang, parent, false);

        return new FragmentKiNangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentKiNangHolder holder, int position) {
        final User current = gridViewModels.get(position);
        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.tvAuther.setText(current.getNameAuthor());
        holder.tvNameBook.setText(current.getNameBook());
        Glide.with(mContext).load(current.getImage()).into(holder.imageClick);
        holder.tvAuther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.linerBook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "AA", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return gridViewModels.size();
    }

    public class FragmentKiNangHolder extends RecyclerView.ViewHolder {

        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;
        private TextView tvNameBook;

        public FragmentKiNangHolder(@NonNull View itemView) {
            super(itemView);
            linerBook = itemView.findViewById(R.id.linerBook_ki_nang);
            imageClick = itemView.findViewById(R.id.image_fragment_ki_nang);
            tvAuther = itemView.findViewById(R.id.tvAuther_fragment_ki_nang);
            tvNameBook = itemView.findViewById(R.id.tvnamBook_fragment_ki_nang);
        }
    }


}
