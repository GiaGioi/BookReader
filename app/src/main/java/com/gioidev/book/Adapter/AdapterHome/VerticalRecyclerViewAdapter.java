package com.gioidev.book.Adapter.AdapterHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gioidev.book.Model.ComicBookModel;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.SliderModel;
import com.gioidev.book.Model.TimerUser;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class VerticalRecyclerViewAdapter extends
        RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRecyclerViewHolder> {
    FirebaseAuth auth;
    private Context mContext;
    RecyclerView rvVertical;
    HorizontalRecyclerViewAdapter mAdapter;
    private ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    public void setConfig(RecyclerView config, Context context, List<HorizontalModel> horizontalModels, List<String> keys){

        rvVertical.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new HorizontalRecyclerViewAdapter(context, horizontalModels);
        rvVertical.setAdapter(mAdapter);
    }

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

        //slider
        ArrayList<SliderModel> sliderModelArrayList = current.getSliderModels();
        SliderAdapter sliderAdapter = new SliderAdapter(mContext,sliderModelArrayList);
        holder.imageSlider.setSliderAdapter(sliderAdapter);

        //recycleview gridview
        ArrayList<GridViewModel> gridviewModel = current.gridViewModelArrayList();
        GridViewRecyclerViewAdapter gridViewRecyclerViewAdapter =
                new GridViewRecyclerViewAdapter(mContext, gridviewModel);
        holder.rvGridView.setHasFixedSize(true);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(mContext, 600);
        holder.rvGridView.setLayoutManager(layoutManager);
        holder.rvGridView.setAdapter(gridViewRecyclerViewAdapter);
        holder.rvGridView.setNestedScrollingEnabled(false);

        //recycleview horizontal
        List<HorizontalModel> singleSectionItems = current.getArrayList();
        holder.tvTitle.setText("Sách mới nhất");

        HorizontalRecyclerViewAdapter itemListDataAdapter =
                new HorizontalRecyclerViewAdapter(mContext, singleSectionItems);
        holder.rvHorizontal.setHasFixedSize(true);

        holder.rvHorizontal.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));

        holder.rvHorizontal.setAdapter(itemListDataAdapter);

        holder.rvHorizontal.setNestedScrollingEnabled(false);

        //comic book viewpager
        ArrayList<ComicBookModel> comicBookModels = current.getComicBookModels();
        ComicBookAdapter comicBookAdapter = new ComicBookAdapter(comicBookModels, mContext);
        holder.viewPager.setAdapter(comicBookAdapter);
        holder.viewPager.setPadding(130, 0, 130, 0);

        holder.tvViewNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, current.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvViewPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, current.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvViewComic.setOnClickListener(new View.OnClickListener() {
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
        private TextView tvViewNewBook;
        private RecyclerView rvHorizontal;
        private TextView tvBookForYou;
        private TextView tvViewPrivate;
        private TextView tvBookStory;
        private TextView tvViewComic;
        private RecyclerView rvGridView;

        private ViewPager viewPager;


        private SliderView imageSlider;
        private TextView tvNameGoodBook;
        private TextView tvTextInBook;


        public VerticalRecyclerViewHolder(View itemView) {
            super(itemView);

            imageSlider =  itemView.findViewById(R.id.imageSlider);
            //text
            tvNameGoodBook = itemView.findViewById(R.id.tvNameGoodBook);
            tvTextInBook = itemView.findViewById(R.id.tvTextInBook);

            rvGridView = itemView.findViewById(R.id.rvGridView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvViewNewBook = itemView.findViewById(R.id.tvViewNewBook);
            rvHorizontal = itemView.findViewById(R.id.rvHorizontal);
            tvBookForYou = itemView.findViewById(R.id.tvBookForYou);
            tvViewPrivate = itemView.findViewById(R.id.tvViewPrivate);

            tvBookStory = itemView.findViewById(R.id.tvBookStory);
            tvViewComic = itemView.findViewById(R.id.tvViewComic);

            viewPager = itemView.findViewById(R.id.viewPager);


        }
    }
}
