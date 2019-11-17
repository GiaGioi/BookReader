package com.gioidev.book.Adapter.AdapterHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gioidev.book.Model.SliderModel;
import com.gioidev.book.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

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

            switch (position) {
                case 1:
                    Glide.with(viewHolder.itemView)
                            .load("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
                            .into(viewHolder.imageViewBackground);
                    break;
                case 2:
                    Glide.with(viewHolder.itemView)
                            .load("https://sachvui.com/cover/2018/biet-hai-long.jpg")
                            .into(viewHolder.imageViewBackground);
                    break;

                case 3:
                    Glide.with(viewHolder.itemView)
                            .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                            .into(viewHolder.imageViewBackground);
                    break;
                default:
                    Glide.with(viewHolder.itemView)
                            .load("https://i.ytimg.com/vi/sCv0AfQi_BI/maxresdefault.jpg")
                            .into(viewHolder.imageViewBackground);
                    break;
            }
            final int image = position;
//            viewHolder.textViewDescription.setText("This is slider item " + (position+1));
            viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toasty.success(context, "This is image" +(image+1),Toast.LENGTH_SHORT).show();
                }
            });

        }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
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
