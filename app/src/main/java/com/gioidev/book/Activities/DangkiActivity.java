package com.gioidev.book.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gioidev.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangkiActivity extends AppCompatActivity {
    private EditText signupemail;
    private EditText signuppassword;
    private EditText requespassword;
    private Button btnLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        auth = FirebaseAuth.getInstance();
        signupemail = (EditText) findViewById(R.id.signupemail);
        signuppassword = (EditText) findViewById(R.id.signuppassword);
        requespassword = (EditText) findViewById(R.id.requespassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void Signup(View view) {
        String email = signupemail.getText().toString().trim();
        String password = signuppassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Xin hãy nhập địa chỉ email", Toast.LENGTH_SHORT).show();
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
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(DangkiActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
//                    + task.isSuccessful()
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(DangkiActivity.this, "Đăng kí thành công" , Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

//                        + task.getException()
                        if (!task.isSuccessful()) {
                            Toast.makeText(DangkiActivity.this, "Đăng kí thất bại,Xin vui lòng nhập đúng và chính xác định dạng email",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(DangkiActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

}
