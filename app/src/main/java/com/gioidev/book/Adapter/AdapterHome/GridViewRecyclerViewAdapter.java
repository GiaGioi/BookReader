package com.gioidev.book.Adapter.AdapterHome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.book.Activities.ReadBookActivity;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class GridViewRecyclerViewAdapter extends
        RecyclerView.Adapter<GridViewRecyclerViewAdapter.GridViewHolder> {

    private Context mContext;
    private ArrayList<GridViewModel> gridViewModels;

    public GridViewRecyclerViewAdapter(Context mContext,
                                       ArrayList<GridViewModel> mArrayList) {
        this.mContext = mContext;
        this.gridViewModels = mArrayList;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gridview, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        final GridViewModel current = gridViewModels.get(position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return gridViewModels.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;
        private TextView tvNameBook;

        public GridViewHolder(View itemView) {
            super(itemView);
            linerBook = itemView.findViewById(R.id.linerBook1);
            imageClick = itemView.findViewById(R.id.imageClick1);
            tvAuther = itemView.findViewById(R.id.tvAuther1);
            tvNameBook = itemView.findViewById(R.id.tvnamBook_Grid);

        }
    }
}
