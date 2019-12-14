package com.gioidev.book.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.book.Adapter.AdapterBook.FragmentBookVipAdapter;
import com.gioidev.book.Adapter.AdapterBook.FragmentComicAdapter;
import com.gioidev.book.Adapter.AdapterBook.FragmentKiNangAdapter;
import com.gioidev.book.Adapter.AdapterBook.FragmentTruyenNganAdapter;
import com.gioidev.book.Model.BooksTruyenNganModel;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_TruyenNgan extends Fragment {
    View v;
    RecyclerView recyclerView;
    FragmentTruyenNganAdapter
            adapter;
    ArrayList<GridViewFragmentModel> gridViewModels = new ArrayList<>();
    DatabaseReference mDatabase;
    GridViewFragmentModel gridViewModel;

    public Fragment_TruyenNgan(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_truyenngan, container, false);
        recyclerView = v.findViewById(R.id.rv_fragment_truyen_ngan);
        getData();
        adapter = new FragmentTruyenNganAdapter(getContext(),gridViewModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter.notifyDataSetChanged();
        return v;
    }
    public void getData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/ShortStory");
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

                    gridViewModel = new GridViewFragmentModel();
                    gridViewModel.setNameBook(nameBook);
                    gridViewModel.setNameAuthor(nameAuthor);
                    gridViewModel.setDescription(Description);
                    gridViewModel.setImage(Image);
                    gridViewModel.setUrl(Url);
                    gridViewModel.setGs(Gs);
                    gridViewModel.setPrice(Price);
                    gridViewModel.setCategory(Category);
                    gridViewModels.add(gridViewModel);

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
