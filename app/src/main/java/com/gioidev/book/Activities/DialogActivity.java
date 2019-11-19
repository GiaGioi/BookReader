package com.gioidev.book.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.gioidev.book.R;

public class DialogActivity extends AppCompatActivity {

    private Button btnDialog;
    Dialog dialog;
    private RelativeLayout rvLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        View view = getLayoutInflater().inflate(R.layout.item_full_screen_dialog,null);

        rvLayout = view.findViewById(R.id.rvLayout);

        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(view);
        dialog.show();

        rvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DialogActivity.this,ReadBookActivity.class));

                dialog.dismiss();

            }
        });
    }
}
