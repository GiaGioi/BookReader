package com.gioidev.book.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gioidev.book.R;

import java.io.File;

public class Fragment_Phong_Thuy extends Fragment {
    View v;

    public Fragment_Phong_Thuy() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_phong_thuy, container, false);
        return v;
    }


}