package com.gioidev.book.Fragment;

import android.animation.ArgbEvaluator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.gioidev.book.Adapter.AdapterHome.ComicBookAdapter;
import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.book.Model.ComicBookModel;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.SliderModel;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;

    private FrameLayout mainContainer;
    private RecyclerView rvVertical;
    ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    VerticalRecyclerViewAdapter mAdapter;

    private SliderView imageSlider;
    private SwipeRefreshLayout swipeRefreshLayout;

    HorizontalModel mHorizontalModel;
    GridViewModel gridViewModel;
    SliderModel sliderModel;
    VerticalModel mVerticalModel;
    List<HorizontalModel> horizontalModels;
    ArrayList<GridViewModel> gridViewModels;
    ArrayList<SliderModel> sliderModels;

    //comic book
    ViewPager viewPager;
    ComicBookAdapter comicBookAdapter;
    ArrayList<ComicBookModel> comicBookModels;
    ComicBookModel comicBookModel;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    HorizontalModel user;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nested_recycleview, container, false);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
//        userId = user.get;
        imageSlider = view.findViewById(R.id.imageSlider);
        rvVertical = view.findViewById(R.id.rvVertical);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color1);
        swipeRefreshLayout.isRefreshing();
        swipeRefreshLayout.setOnRefreshListener(this);

        mainContainer = view.findViewById(R.id.main_container);
        rvVertical = view.findViewById(R.id.rvVertical);

        timer = new Timer();
        rvVertical.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new VerticalRecyclerViewAdapter(getContext(), mArrayList);
        rvVertical.setAdapter(mAdapter);
        setDataOnVerticalRecyclerView();
        getDataHorizontal();
        getDataGridview();
        getDataSlider();
        getDataComicBook();

        return view;

    }

    public void getDataHorizontal() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("Epub/SkillBook");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horizontalModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());


                    String nameBook = String.valueOf(snapshot.child("NameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("NameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    String Image = String.valueOf(snapshot.child("Image").getValue());
                    String Url = String.valueOf(snapshot.child("Url").getValue());
                    String Gs = String.valueOf(snapshot.child("Gs").getValue());
                    String Price = String.valueOf(snapshot.child("Price").getValue());
                    String Category = String.valueOf(snapshot.child("Category").getValue());
                    mHorizontalModel = new HorizontalModel();
                    mHorizontalModel.setNameBook(nameBook);
                    mHorizontalModel.setPrice(Price);
                    mHorizontalModel.setUrl(Url);
                    mHorizontalModel.setNameAuthor(nameAuthor);
                    mHorizontalModel.setImage(Image);
                    mHorizontalModel.setGs(Gs);
                    mHorizontalModel.setDescription(Description);
                    mHorizontalModel.setCategory(Category);

                    horizontalModels.add(mHorizontalModel);

                    mVerticalModel.setArrayList(horizontalModels);
                    mAdapter.notifyDataSetChanged();

                    Log.e(TAG, "onDataChange: " + mHorizontalModel.getImage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataGridview() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/SkillBook");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gridViewModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.child("Url").getKey());

//                    String url2 = String.valueOf(snapshot.child("Url").getKey());
//                    Log.e(TAG, "onDataChange: " + url2 );
                    String nameBook = String.valueOf(snapshot.child("NameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("NameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    String Image = String.valueOf(snapshot.child("Image").getValue());
                    String Url = String.valueOf(snapshot.child("Url").getValue());
                    String Gs = String.valueOf(snapshot.child("Gs").getValue());
                    String Price = String.valueOf(snapshot.child("Price").getValue());
                    String Category = String.valueOf(snapshot.child("Category").getValue());

                    gridViewModel = new GridViewModel();
                    gridViewModel.setNameBook(nameBook);
                    gridViewModel.setPrice(Price);
                    gridViewModel.setUrl(Url);
                    gridViewModel.setNameAuthor(nameAuthor);
                    gridViewModel.setImage(Image);
                    gridViewModel.setGs(Gs);
                    gridViewModel.setDescription(Description);
                    gridViewModel.setCategory(Category);

                    gridViewModels.add(gridViewModel);

                    mVerticalModel.setGridViewModels(gridViewModels);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataSlider() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("Slider");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sliderModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());

                    String nameBook = String.valueOf(snapshot.child("NameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("NameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    String Image = String.valueOf(snapshot.child("Image").getValue());
                    String Url = String.valueOf(snapshot.child("Url").getValue());
                    String Gs = String.valueOf(snapshot.child("Gs").getValue());
                    String Price = String.valueOf(snapshot.child("Price").getValue());
                    String Category = String.valueOf(snapshot.child("Category").getValue());
                    sliderModel = new SliderModel();
                    sliderModel.setNameBook(nameBook);
                    sliderModel.setPrice(Price);
                    sliderModel.setUrl(Url);
                    sliderModel.setNameAuthor(nameAuthor);
                    sliderModel.setImage(Image);
                    sliderModel.setGs(Gs);
                    sliderModel.setDescription(Description);
                    sliderModel.setCategory(Category);

                    sliderModels.add(sliderModel);

                    mVerticalModel.setSliderModels(sliderModels);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getDataComicBook() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/Comic");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comicBookModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String nameBook = String.valueOf(snapshot.child("NameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("NameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    String url = String.valueOf(snapshot.child("Url").getValue());
                    String Image = String.valueOf(snapshot.child("Image").getValue());
                    String Gs = String.valueOf(snapshot.child("Gs").getValue());
                    String Price = String.valueOf(snapshot.child("Price").getValue());
                    String Category = String.valueOf(snapshot.child("Category").getValue());

                    comicBookModel = new ComicBookModel();
                    comicBookModel.setNameBook(nameBook);
                    comicBookModel.setPrice(Price);
                    comicBookModel.setUrl(url);
                    comicBookModel.setNameAuthor(nameAuthor);
                    comicBookModel.setImage(Image);
                    comicBookModel.setGs(Gs);
                    comicBookModel.setDescription(Description);
                    comicBookModel.setCategory(Category);

                    comicBookModels.add(comicBookModel);

                    mVerticalModel.setComicBookModels(comicBookModels);
                    mAdapter.notifyDataSetChanged();

                    Log.e(TAG, "onDataChange: " + url );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDataOnVerticalRecyclerView() {
        for (int i = 1; i <= 1; i++) {

            mVerticalModel = new VerticalModel();

            mVerticalModel.setTitle("Title " + i);

            //horizontal view
            for (int j = 0; j < 10; j++) {
                horizontalModels = new ArrayList<>();
                mHorizontalModel = new HorizontalModel();
                horizontalModels.add(mHorizontalModel);
            }
            mVerticalModel.setArrayList(horizontalModels);

            //slider
            for (int j = 0; j < 4; j++) {
                sliderModels = new ArrayList<>();
                sliderModel = new SliderModel();
                sliderModels.add(sliderModel);
            }
            mVerticalModel.setSliderModels(sliderModels);

            //gridview
            for (int j = 0; j < 6; j++) {
                //gridview
                gridViewModels = new ArrayList<>();
                gridViewModel = new GridViewModel();
                gridViewModels.add(gridViewModel);
            }
            mVerticalModel.setGridViewModels(gridViewModels);

            //comic book
            for (int j = 0; j < 4; j++) {
                //gridview
                comicBookModels = new ArrayList<>();
                comicBookModel = new ComicBookModel();
                comicBookModels.add(comicBookModel);
            }
            mVerticalModel.setComicBookModels(comicBookModels);
//            Integer[] colors_temp = {
//                    getResources().getColor(R.color.color1),
//                    getResources().getColor(R.color.color2),
//                    getResources().getColor(R.color.color3),
//                    getResources().getColor(R.color.colorBook)
//            };
//            colors = colors_temp;
//            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                    if (position < (comicBookAdapter.getCount() -1) && position < (colors.length - 1)) {
//                        viewPager.setBackgroundColor(
//
//                                (Integer) argbEvaluator.evaluate(
//                                        positionOffset,
//                                        colors[position],
//                                        colors[position + 1]
//                                )
//                        );
//                    }
//
//                    else {
//                        viewPager.setBackgroundColor(colors[colors.length - 1]);
//                    }
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });


            mArrayList.add(mVerticalModel);

        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataHorizontal();
                getDataGridview();
                getDataSlider();
                getDataComicBook();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
