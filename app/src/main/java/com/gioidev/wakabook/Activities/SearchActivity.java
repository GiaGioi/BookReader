package com.gioidev.wakabook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gioidev.book.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {

    private EditText tvSearch;
    private RecyclerView rvSearch;
    FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvSearch = findViewById(R.id.tvSearch);
        rvSearch = findViewById(R.id.rvSearch);

//        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = tvSearch.getText().toString();

//                firebaseUserSearch(searchText);
//                firebaseDatabase.getReference("pdf")
//                        .orderByChild("childNode")
//                        .startAt("[a-zA-Z0-9]*")
//                        .endAt(searchText);

            }
        });

    }
    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

//        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        databaseReference.orderByChild("NameBook")
                .startAt(searchText)
                .endAt(searchText+"\uf8ff");

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
