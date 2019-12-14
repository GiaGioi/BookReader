package com.gioidev.book.Adapter.AdapterBook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.gioidev.book.Activities.ReadBookActivity;
import com.gioidev.book.Fragment.BookcaseFragment;
import com.gioidev.book.Model.GridViewFragmentModel;
import com.gioidev.book.Model.User;
import com.gioidev.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FragmentKiNangAdapter extends
        RecyclerView.Adapter<FragmentKiNangAdapter.FragmentKiNangHolder> {

    private Context mContext;
    private ArrayList<User> gridViewModels;
    private Activity activity;
    DatabaseReference databaseReference;

    public FragmentKiNangAdapter(Context mContext, ArrayList<User> gridViewModels) {
        this.mContext = mContext;
        this.gridViewModels = gridViewModels;
    }
    @NonNull
    @Override
    public FragmentKiNangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_ki_nang, parent, false);

        return new FragmentKiNangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentKiNangHolder holder, int position) {
        final User current = gridViewModels.get(position);
        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.tvAuther.setText(current.getNameAuthor());
        holder.tvNameBook.setText(current.getNameBook());
        Glide.with(mContext).load(current.getImage()).into(holder.imageClick);
        holder.tvAuther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.linerBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = mContext.getSharedPreferences("Data", Context.MODE_PRIVATE);
                preferences.edit().putString("Horizontal","1").apply();

                Intent intent = new Intent(mContext, ReadBookActivity.class);
                intent.putExtra("Url", String.valueOf(current.getUrl()));
                intent.putExtra("NameBook", current.getNameBook());
                intent.putExtra("NameAuthor", current.getNameAuthor());
                intent.putExtra("Description", current.getDescription());
                intent.putExtra("Gs", current.getGs());
                intent.putExtra("Image", current.getImage());
                intent.putExtra("Price", current.getPrice());
                intent.putExtra("Category", current.getCategory());
                mContext.startActivity(intent);

                Log.e("TAG", "onClick: " + current);
            }
        });
        holder.linerBook.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onLongClick(View view) {
                new iOSDialogBuilder(mContext)
                        .setTitle(mContext.getString(R.string.example_title))
                        .setSubtitle(mContext.getString(R.string.example_subtitle))
                        .setBoldPositiveLabel(true)
                        .setCancelable(false)
                        .setPositiveListener(mContext.getString(R.string.oke),new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                Toasty.success(mContext,"Deleted!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeListener(mContext.getString(R.string.dismiss), new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build().show();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return gridViewModels.size();
    }

    public class FragmentKiNangHolder extends RecyclerView.ViewHolder {

        private LinearLayout linerBook;
        private ImageView imageClick;
        private TextView tvAuther;
        private TextView tvNameBook;

        public FragmentKiNangHolder(@NonNull View itemView) {
            super(itemView);
            linerBook = itemView.findViewById(R.id.linerBook_ki_nang);
            imageClick = itemView.findViewById(R.id.image_fragment_ki_nang);
            tvAuther = itemView.findViewById(R.id.tvAuther_fragment_ki_nang);
            tvNameBook = itemView.findViewById(R.id.tvnamBook_fragment_ki_nang);
        }
    }


}
