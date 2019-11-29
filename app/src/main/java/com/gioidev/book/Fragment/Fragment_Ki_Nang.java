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

import com.gioidev.book.Adapter.AdapterBook.FragmentKiNangAdapter;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Ki_Nang extends Fragment{
    View v;
    RecyclerView recyclerView;
    FragmentKiNangAdapter adapter;
    ArrayList<GridViewFragmentModel> gridViewModels;
    DatabaseReference mDatabase;
    GridViewFragmentModel gridViewModel;


    public Fragment_Ki_Nang(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ki_nang,container,false);

        recyclerView = v.findViewById(R.id.rv_fragment_ki_nang);
        adapter = new FragmentKiNangAdapter(getContext(),gridViewModels);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                    gridViewModels = new ArrayList<>();
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

//                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
