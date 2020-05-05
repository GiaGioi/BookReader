package com.gioidev.wakabook.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.wakabook.Adapter.AdapterBook.ComicBookFragmentAdapter;
import com.gioidev.wakabook.Model.ComicBookModel;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentComic extends Fragment {

    private RecyclerView rvfragmentcomic;
    ComicBookFragmentAdapter adapter;
    ArrayList<ComicBookModel> models = new ArrayList<>();
    ComicBookModel comicBookModel;

    DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);

        rvfragmentcomic = view.findViewById(R.id.rvfragmentcomic);
        rvfragmentcomic.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new ComicBookFragmentAdapter(getContext(),models);
        rvfragmentcomic.setAdapter(adapter);

        getDataHorizontal();
        return view;
    }

    public void getDataHorizontal() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/Comic");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
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

                    comicBookModel = new ComicBookModel();
                    comicBookModel.setNameBook(nameBook);
                    comicBookModel.setPrice(Price);
                    comicBookModel.setUrl(Url);
                    comicBookModel.setNameAuthor(nameAuthor);
                    comicBookModel.setImage(Image);
                    comicBookModel.setGs(Gs);
                    comicBookModel.setDescription(Description);
                    comicBookModel.setCategory(Category);

                    models.add(comicBookModel);
                    adapter.notifyDataSetChanged();

                    Log.e("TAG", "onDataChange: " + comicBookModel.getImage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
