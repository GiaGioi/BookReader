package com.gioidev.book.Adapter.AdapterBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class FragmentKiNangAdapter extends
        RecyclerView.Adapter<FragmentKiNangAdapter.FragmentKiNangHolder> {

    private Context mContext;
    private ArrayList<GridViewFragmentModel> gridViewModels;

    public FragmentKiNangAdapter(Context mContext, ArrayList<GridViewFragmentModel> gridViewModels) {
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
