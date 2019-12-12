package com.gioidev.book.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity {

    private EditText tvSearch;
    private RecyclerView rvSearch;


    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvSearch = findViewById(R.id.tvSearch);
        rvSearch = findViewById(R.id.rvSearch);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = tvSearch.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }
    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");


    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }





    }
}