package com.gioidev.wakabook.Activities.Manhinhchao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gioidev.wakabook.Activities.LoginActivity;
import com.gioidev.book.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ManhinhchaoActivity extends AppCompatActivity {

    Animation anim_btn,nothingtocome,nothingtoup;
    private TextView tvText;
    private TextView tvText2;
    private ImageView imageView;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);



        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    "com.gioidev.book",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        tvText = findViewById(R.id.tvText);
        tvText2 = findViewById(R.id.tvText2);
        imageView = findViewById(R.id.imageView);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManhinhchaoActivity.this,Manhinhchao2Activity.class));
                finish();
            }
        });

        if (firstStart){
            firststart1();
        }
        else{
            startActivity(new Intent(ManhinhchaoActivity.this,LoginActivity.class));
            finish();
        }

//        anim_btn = AnimationUtils.loadAnimation(this, R.anim.anim_btn);
//        nothingtocome = AnimationUtils.loadAnimation(this, R.anim.nothingtocome);
//        nothingtoup = AnimationUtils.loadAnimation(this, R.anim.nothingtoup);
//
//        imageView.startAnimation(anim_btn);
//        tvText.startAnimation(nothingtocome);
//        tvText2.startAnimation(nothingtocome);
//        btnContinue.startAnimation(nothingtoup);
    }
    public void firststart1(){
        anim_btn = AnimationUtils.loadAnimation(this, R.anim.anim_btn);
        nothingtocome = AnimationUtils.loadAnimation(this, R.anim.nothingtocome);
        nothingtoup = AnimationUtils.loadAnimation(this, R.anim.nothingtoup);

        imageView.startAnimation(anim_btn);
        tvText.startAnimation(nothingtocome);
        tvText2.startAnimation(nothingtocome);
        btnContinue.startAnimation(nothingtoup);

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();

    }
}
