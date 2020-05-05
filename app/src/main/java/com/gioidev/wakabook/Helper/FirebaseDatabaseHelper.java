package com.gioidev.wakabook.Helper;

import androidx.annotation.NonNull;

import com.gioidev.wakabook.Model.HorizontalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<HorizontalModel> horizontalModels = new ArrayList<>();

    public interface OnDataLoadFirebase{
        void DataIsLoaded(List<HorizontalModel> horizontalModels, List<String> keys);
    }
    public FirebaseDatabaseHelper() {
         firebaseDatabase = FirebaseDatabase.getInstance();
         reference = firebaseDatabase.getReference("books/PDF/SkillBook");
    }
    public void ReadBook(final OnDataLoadFirebase dataLoadFirebase){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horizontalModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());
                    HorizontalModel horizontalModel = snapshot.getValue(HorizontalModel.class);
                    horizontalModels.add(horizontalModel);
                }
                dataLoadFirebase.DataIsLoaded(horizontalModels,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
