package com.gioidev.book.Adapter.AdapterBook;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.book.Activities.ReadBookActivity;
import com.gioidev.book.Model.BookVipModel;
import com.gioidev.book.Model.BooksTruyenNganModel;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class FragmentTruyenNganAdapter extends
        RecyclerView.Adapter<FragmentTruyenNganAdapter.FragmentTruyenNganHolder> {

    private Context mContext;
    private ArrayList<GridViewFragmentModel> gridViewModels;

    public FragmentTruyenNganAdapter(Context mContext, ArrayList<GridViewFragmentModel> gridViewModels) {
        this.mContext = mContext;
        this.gridViewModels = gridViewModels;
    }

    @NonNull
    @Override
    public FragmentTruyenNganHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_truyen_ngan, parent, false);

        return new FragmentTruyenNganHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentTruyenNganHolder holder, int position) {
        final GridViewFragmentModel current = gridViewModels.get(position);
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
        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = mContext.getSharedPreferences("Data", Context.MODE_PRIVATE);
                preferences.edit().putString("Horizontal","1").apply();

                Intent intent = new Intent(mContext, ReadBookActivity.class);
                intent.putExtra("NameBook", current.getNameBook());
                intent.putExtra("NameAuthor", current.getNameAuthor());
                intent.putExtra("Url", current.getUrl());
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Gs", current.getGs());
                intent.putExtra("Image", current.getImage());
                intent.putExtra("Price", current.getPrice());
                intent.putExtra("Category", current.getCategory());
                mContext.startActivity(intent);
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

    public class FragmentTruyenNganHolder extends RecyclerView.ViewHolder {
        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;
        private TextView tvNameBook;

        public FragmentTruyenNganHolder(@NonNull View itemView) {
            super(itemView);
            linerBook = itemView.findViewById(R.id.linerBook_shortstory);
            imageClick = itemView.findViewById(R.id.image_fragment_shortstory);
            tvAuther = itemView.findViewById(R.id.tvAuther_fragment_shortstory);
            tvNameBook = itemView.findViewById(R.id.tvnamBook_fragment_shortstory);
        }
    }
}
