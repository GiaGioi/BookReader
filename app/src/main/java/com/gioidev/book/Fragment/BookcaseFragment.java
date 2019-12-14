package com.gioidev.book.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gioidev.book.Activities.SearchActivity;
import com.gioidev.book.Adapter.AdapterBook.ViewPagerAdapter;
import com.gioidev.book.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class BookcaseFragment extends Fragment {
    private EditText tvSearch;
    private TabLayout tabBookCase;
    private ViewPager viewpageBookCase;
    ViewPagerAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_case, container, false);

        tabBookCase = view.findViewById(R.id.tab_book_case);
        viewpageBookCase = view.findViewById(R.id.viewpage_book_case);
        tvSearch = view.findViewById(R.id.tvSearch);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(new Fragment_Ki_Nang(),"Tủ sách");
        viewpageBookCase.setAdapter(adapter);
        tabBookCase.setupWithViewPager(viewpageBookCase);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        return view;
    }

}
