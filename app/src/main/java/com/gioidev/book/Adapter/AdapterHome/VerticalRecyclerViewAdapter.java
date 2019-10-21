package com.gioidev.book.Adapter.AdapterHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;

import java.util.ArrayList;

public class VerticalRecyclerViewAdapter extends
        RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRecyclerViewHolder>{

    private Context mContext;
    private ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    public VerticalRecyclerViewAdapter(Context mContext, ArrayList<VerticalModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public VerticalRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical, parent, false);
        return new VerticalRecyclerViewHolder(view);
    }

    public void setList(ArrayList<VerticalModel> mArrayList){
        this.mArrayList.addAll(mArrayList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VerticalRecyclerViewHolder holder, int position) {

        final VerticalModel current = mArrayList.get(position);

        final String strTitle = current.getTitle();

        ArrayList<HorizontalModel> singleSectionItems = current.getArrayList();

        holder.tvTitle.setText(strTitle);

        HorizontalRecyclerViewAdapter itemListDataAdapter =
                new HorizontalRecyclerViewAdapter(mContext, singleSectionItems);

        holder.rvHorizontal.setHasFixedSize(true);
        holder.rvHorizontal.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizontal.setAdapter(itemListDataAdapter);

        holder.rvHorizontal.setNestedScrollingEnabled(false);

        holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, current.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()  {
        return mArrayList.size();
    }

    class VerticalRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvViewAll;
        private RecyclerView rvHorizontal;
        private TextView tvBookForYou;
        private TextView tvViewAll2;
        private RecyclerView rvGridView;
        private TextView tvBookStory;
        private TextView tvViewAll3;

        public VerticalRecyclerViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvViewAll = itemView.findViewById(R.id.tvViewAll);
            rvHorizontal = itemView.findViewById(R.id.rvHorizontal);
            tvBookForYou = itemView.findViewById(R.id.tvBookForYou);
            tvViewAll2 = itemView.findViewById(R.id.tvViewAll2);
            rvGridView = itemView.findViewById(R.id.rvGridView);
            tvBookStory = itemView.findViewById(R.id.tvBookStory);
            tvViewAll3 = itemView.findViewById(R.id.tvViewAll3);

        }
    }
}
