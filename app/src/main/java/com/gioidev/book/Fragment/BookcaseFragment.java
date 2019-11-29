package com.gioidev.book.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.gioidev.book.Activities.HomeActivity;
import com.gioidev.book.Adapter.AdapterBook.ViewPagerAdapter;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.SliderModel;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.gioidev.book.Utils.Functions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BookcaseFragment extends Fragment {

    private TabLayout tabBookCase;
    private ViewPager viewpageBookCase;
    ViewPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_case, container, false);
//        FragmentActivity fragmentActivity = null;
        tabBookCase = view.findViewById(R.id.tab_book_case);
        viewpageBookCase = view.findViewById(R.id.viewpage_book_case);


//        Fragment_Ki_Nang homeFragment = new Fragment_Ki_Nang();
//        Functions.changeMainFragment(getActivity(), homeFragment);
        viewpageBookCase.setAdapter(adapter);
        tabBookCase.setupWithViewPager(viewpageBookCase);


        //add fragment


        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(new Fragment_Ki_Nang(),"Sách kĩ năng");
        adapter.AddFragment(new Fragment_Khach_Hang(),"Marketing - Khách hàng");
        adapter.AddFragment(new Fragment_Phong_Thuy(),"Tử vi - Phong thủy");

    }
    //    private void setDataOnVerticalRecyclerView() {
//        for (int i = 1; i <= 1; i++) {
//
//            mVerticalModel = new VerticalModel();
//
//            mVerticalModel.setTitle("Title " + i);
//
//            //horizontal view
//            for (int j = 0; j < 10; j++) {
//                horizontalModels = new ArrayList<>();
//                mHorizontalModel = new HorizontalModel();
//                horizontalModels.add(mHorizontalModel);
//            }
//            mVerticalModel.setArrayList(horizontalModels);
//
//            //slider
//            for (int j = 0; j < 4; j++) {
//                sliderModels = new ArrayList<>();
//                sliderModel = new SliderModel();
//                sliderModels.add(sliderModel);
//            }
//            mVerticalModel.setSliderModels(sliderModels);
//
//            for (int j = 0; j < 6; j++) {
//                //gridview
//                gridViewModels = new ArrayList<>();
//                gridViewModel = new GridViewModel();
//                gridViewModels.add(gridViewModel);
//            }
//            mVerticalModel.setGridViewModels(gridViewModels);
//
//
//            mArrayList.add(mVerticalModel);
//
//        }
//        mAdapter.notifyDataSetChanged();
//    }
}
