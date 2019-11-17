package com.gioidev.book.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gioidev.book.Adapter.AdapterBook.ViewPagerAdapter;
import com.gioidev.book.Fragment.Fragment_Khach_Hang;
import com.gioidev.book.Fragment.Fragment_Ki_Nang;
import com.gioidev.book.Fragment.Fragment_Phong_Thuy;
import com.gioidev.book.R;
import com.google.android.material.tabs.TabLayout;

public class BookcaseActivity extends Fragment {

    private TabLayout tabBookCase;
    private ViewPager viewpageBookCase;
    ViewPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_case, container, false);

        tabBookCase = view.findViewById(R.id.tab_book_case);
        viewpageBookCase = view.findViewById(R.id.viewpage_book_case);

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        //add fragment
        adapter.AddFragment(new Fragment_Khach_Hang(),"Marketing - Khách hàng");
        adapter.AddFragment(new Fragment_Ki_Nang(),"Sách tâm lí - Kĩ năng sống");
        adapter.AddFragment(new Fragment_Phong_Thuy(),"Tử vi - Phong thủy");
        viewpageBookCase.setAdapter(adapter);
        tabBookCase.setupWithViewPager(viewpageBookCase);

        return view;

    }

//    @Override
//    protected void onCreateView(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book_case);
//
//
//    }
}
