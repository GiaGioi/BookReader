package com.gioidev.book.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gioidev.book.Adapter.AdapterBook.FragmentKiNangAdapter;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.Model.User;
import com.gioidev.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ArrayList<User> gridViewModels = new ArrayList<>();
    User gridViewModel;
    private EditText tvSearch;



    DatabaseReference mDatabase;

    public Fragment_Ki_Nang(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ki_nang,container,false);
        tvSearch = (EditText) v.findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = tvSearch.getText().toString();
                mDatabase.orderByChild("nameBook")
                        .startAt(searchText)
                        .endAt(searchText+"\uf8ff");
            }
        });
        recyclerView = v.findViewById(R.id.rv_fragment_ki_nang);
        adapter = new FragmentKiNangAdapter(getContext(),gridViewModels);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
        getUID();
        getData();
        return v;
    }
    private void getUID(){
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        String getUid = user1.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child("PDF").child(getUid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());
                    String Uid = String.valueOf(snapshot.child("uid").getValue());
                    if(getUid.equals(Uid)){
                        getData();
                    }else {
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("TAG", "getUid: " + getUid);
                    Log.e("TAG", "Uid: " + Uid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getData(){
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        assert user1 != null;
        String getUid = user1.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child("PDF").child(getUid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gridViewModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());

                    String nameBook = String.valueOf(snapshot.child("nameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("nameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("description").getValue());
                    String Image = String.valueOf(snapshot.child("image").getValue());
                    String Url = String.valueOf(snapshot.child("url").getValue());
                    String Gs = String.valueOf(snapshot.child("gs").getValue());
                    String Price = String.valueOf(snapshot.child("price").getValue());
                    String Category = String.valueOf(snapshot.child("category").getValue());
                    String Uid = String.valueOf(snapshot.child("uid").getValue());

                    gridViewModel = new User();
                    gridViewModel.setNameBook(nameBook);
                    gridViewModel.setNameAuthor(nameAuthor);
                    gridViewModel.setDescription(Description);
                    gridViewModel.setImage(Image);
                    gridViewModel.setUrl(Url);
                    gridViewModel.setGs(Gs);
                    gridViewModel.setPrice(Price);
                    gridViewModel.setCategory(Category);
                    gridViewModel.setUid(Uid);
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
