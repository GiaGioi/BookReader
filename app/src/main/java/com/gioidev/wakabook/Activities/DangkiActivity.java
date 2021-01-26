package com.gioidev.wakabook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gioidev.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangkiActivity extends AppCompatActivity {
    private EditText signupemail;
    private EditText signuppassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText confirmspassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        auth = FirebaseAuth.getInstance();
        signupemail = (EditText) findViewById(R.id.signupemail);
        signuppassword = (EditText) findViewById(R.id.signuppassword);
        confirmspassword = (EditText) findViewById(R.id.confirmspassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    public void Signup(View view) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = signupemail.getText().toString().trim();
        String password = signuppassword.getText().toString().trim();
        String confirmpassword = confirmspassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Xin hãy nhập địa chỉ email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            Toast.makeText(getApplicationContext(), "Xin hãy nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Xin hãy nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu của bạn quá ngắn, Mật khẩu phải lớn hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "Không được để trống mục nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmpassword.equals(password)){
            Toast.makeText(getApplicationContext(), "Hãy nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(DangkiActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
////                    + task.isSuccessful()
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(DangkiActivity.this, "Đăng kí thất bại" , Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//
////                        + task.getException()
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(DangkiActivity.this, "Đăng kí thành công",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            startActivity(new Intent(DangkiActivity.this, LoginActivity.class));
//                            finish();
//                        }
//                    }
//                });
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DangkiActivity.this, "Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangkiActivity.this,LoginActivity.class);
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangkiActivity.this, "Đăng kí thất bại, Đã có người dùng tên tài khoản này",
                                    Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }
                });
    }

    public void nextlogin(View view) {
        Intent intent = new Intent(DangkiActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
