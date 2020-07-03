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

import com.gioidev.wakabook.Adapter.AdapterBook.FragmentBookVipAdapter;
import com.gioidev.wakabook.Model.BookVipModel;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Book_Vip extends Fragment {

    private RecyclerView rvfragmentbookvip;
    FragmentBookVipAdapter adapter;
    ArrayList<BookVipModel> models = new ArrayList<>();
    BookVipModel bookVipModel;

    DatabaseReference mDatabase;
    public Fragment_Book_Vip(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_vip, container, false);

        rvfragmentbookvip = view.findViewById(R.id.rvfragmentbookvip);
        rvfragmentbookvip.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new  FragmentBookVipAdapter(getContext(),models);
        rvfragmentbookvip.setAdapter(adapter);

        getDataHorizontal();
        return view;
    }
    public void getDataHorizontal() {
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/BookVip");
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
                    String vip = String.valueOf(snapshot.child("vip").getValue());

                    bookVipModel = new BookVipModel();
                    bookVipModel.setNameBook(nameBook);
                    bookVipModel.setPrice(Price);
                    bookVipModel.setUrl(Url);
                    bookVipModel.setNameAuthor(nameAuthor);
                    bookVipModel.setImage(Image);
                    bookVipModel.setGs(Gs);
                    bookVipModel.setDescription(Description);
                    bookVipModel.setCategory(Category);
                    bookVipModel.setVip(vip);

                    models.add(bookVipModel);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
