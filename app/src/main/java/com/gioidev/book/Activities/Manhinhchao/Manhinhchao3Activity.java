package com.gioidev.book.Activities.Manhinhchao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gioidev.book.Activities.HomeActivity;
import com.gioidev.book.Activities.LoginActivity;
import com.gioidev.book.R;

public class Manhinhchao3Activity extends AppCompatActivity {

    Animation anim_btn,nothingtocome,nothingtoup;
    private TextView tvText;
    private TextView tvText2;
    private ImageView imageView;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao3);
        tvText = findViewById(R.id.tvText);
        tvText2 = findViewById(R.id.tvText2);
        imageView = findViewById(R.id.imageView);
        btnContinue = findViewById(R.id.btnContinue);

        anim_btn = AnimationUtils.loadAnimation(this, R.anim.anim_btn);
        nothingtocome = AnimationUtils.loadAnimation(this, R.anim.nothingtocome);
        nothingtoup = AnimationUtils.loadAnimation(this, R.anim.nothingtoup);

        imageView.startAnimation(anim_btn);
        tvText.startAnimation(nothingtocome);
        tvText2.startAnimation(nothingtocome);
        btnContinue.startAnimation(nothingtoup);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Manhinhchao3Activity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
