package com.gioidev.wakabook.Adapter.AdapterHome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gioidev.wakabook.Activities.ReadBookActivity;
import com.gioidev.wakabook.Model.SliderModel;
import com.gioidev.book.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private ArrayList<SliderModel> mArrayList;

    public SliderAdapter(Context context, ArrayList<SliderModel> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_viewpager, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
            final SliderModel model = mArrayList.get(position);

            Glide.with(context).load(model.getImage()).into(viewHolder.imageViewBackground);
            viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences preferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
                    preferences.edit().putString("Horizontal","3").apply();

                    Intent intent = new Intent(context, ReadBookActivity.class);
                    intent.putExtra("Url", String.valueOf(model.getUrl()));
                    intent.putExtra("NameBook", model.getNameBook());
                    intent.putExtra("NameAuthor", model.getNameAuthor());
                    intent.putExtra("Description", model.getDescription());
                    intent.putExtra("Gs", model.getGs());
                    intent.putExtra("Image", model.getImage());
                    intent.putExtra("Price", model.getPrice());
                    intent.putExtra("Category", model.getCategory());
                    context.startActivity(intent);

                    Log.e("TAG", "onClick: " + model.getImage());
                }
            });

        }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mArrayList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
//        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imageViewpager);
//            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            this.itemView = itemView;
        }
    }
}
