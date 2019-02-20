package com.lib.user.libraryproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText id, pw;
    private Button login;
    private TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);
        login = findViewById(R.id.login_login);
        signin = findViewById(R.id.login_signin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itext = id.getText().toString();
                String ptext = pw.getText().toString();

                if(itext.isEmpty() || ptext.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "정보를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(itext, ptext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
