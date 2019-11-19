package com.gioidev.book.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
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

public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

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
    VerticalModel mVerticalModel;
    List<HorizontalModel> horizontalModels;
    ArrayList<GridViewModel> gridViewModels;

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

        return view;

    }
    public void getDataHorizontal(){
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/SkillBook");
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
    public void getDataGridview(){
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/SkillBook");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gridViewModels.clear();
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
                    gridViewModel = new GridViewModel();
                    gridViewModel.setNameBook(nameBook);
                    gridViewModel.setPrice(Price);
                    gridViewModel.setUrl(Url);
                    gridViewModel.setNameAuthor(nameAuthor);
                    gridViewModel.setImage(Image);
                    gridViewModel.setGs(Gs);
                    gridViewModel.setDescription(Description);
                    mHorizontalModel.setCategory(Category);

                    gridViewModels.add(gridViewModel);

                    mVerticalModel.setArrayList(horizontalModels);
                    mAdapter.notifyDataSetChanged();
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
            ArrayList<SliderModel> sliderModels = new ArrayList<>();

            for (int j = 0; j <= 5; j++) {
                SliderModel sliderModel = new SliderModel();
                sliderModel.getImage();
                sliderModels.add(sliderModel);
            }
            mVerticalModel.setSliderModels(sliderModels);
            for (int j = 0; j <6; j++) {
                //gridview
                gridViewModels = new ArrayList<>();
                gridViewModel = new GridViewModel();
                gridViewModels.add(gridViewModel);
            }
            mVerticalModel.setGridViewModels(gridViewModels);


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
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
