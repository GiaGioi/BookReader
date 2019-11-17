package com.gioidev.book.Adapter.AdapterBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.book.Model.Books;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.R;

import java.util.ArrayList;
import java.util.List;

public class BookViewAdapter extends
        RecyclerView.Adapter<BookViewAdapter.HorizontalViewHolder> {

    private Context mContext;
    private List<Books> mArrayList;

    public BookViewAdapter(Context mContext,
                           List<Books> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hozizontal, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        final Books current = mArrayList.get(position);

        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Sách" + current.getNameBook(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvAuther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Tác giả" + current.getNameAuthor(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, current.getNameBook(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            linerBook = itemView.findViewById(R.id.linerBook);
            imageClick = itemView.findViewById(R.id.imageClick);
            tvAuther = itemView.findViewById(R.id.tvAuther);

        }
    }
}
