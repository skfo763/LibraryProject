package com.lib.user.libraryproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText idtext, pwtext, name, pwcheck, univ;
    private Button univbtn, check;
    private LinearLayout linearLayout;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    String[] univItems = {"가톨릭대학교","건국대학교","경기대학교","경희대학교","고려대학교","광운대학교","국민대학교","동국대학교","명지대학교","삼육대학교",
            "상명대학교","서강대학교","서경대학교","서울대학교","서울과학기술대학교","서울시립대학교","성공회대학교","성균관대학교","세종대학교"
            ,"숭실대학교","연세대학교","중앙대학교","총신대학교","한국외국어대학교","한성대학교","한양대학교","홍익대학교","케이씨대학교","덕성여자대학교","동덕여자대학교",
            "서울여자대학교","성신여자대학교","이화여자대학교","서울교육대학교","추계예술대학교","한국예술종합학교","한국체육대학교","육군사관학교"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        linearLayout = findViewById(R.id.signin_progress_layout);
        idtext = findViewById(R.id.signin_getid);
        name = findViewById(R.id.signin_getname);
        pwtext = findViewById(R.id.signin_getpw);
        pwcheck = findViewById(R.id.signin_checkpw);
        univ = findViewById(R.id.signin_checkuniv);
        univbtn = findViewById(R.id.checkuniv);
        check = findViewById(R.id.signin_signin);

        //대학선택버튼
        univbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setItems(univItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        univ.setText(univItems[which]);
                    }
                }).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                final String id = idtext.getText().toString();
                final String nm = name.getText().toString();
                final String pw = pwtext.getText().toString();
                String pwchk = pwcheck.getText().toString();
                final String uv = univ.getText().toString();

                if(id.isEmpty() || nm.isEmpty() || pw.isEmpty() || pwchk.isEmpty() || uv.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if(!pw.equals(pwchk)) {
                        Toast.makeText(SignInActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    addData(id, pw, nm, uv);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void addData(String id, String pw, final String nm, final String uv) {
        auth.signInWithEmailAndPassword(id, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name.getText().toString())
                            .build();
                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                    Map<String, Object> data = new HashMap<>();
                    data.put("uid", auth.getCurrentUser().getUid());
                    data.put("name", nm);
                    data.put("university", uv);
                    data.put("pushToken", FirebaseInstanceId.getInstance().getToken());
                    FirebaseFirestore.getInstance().collection("users").document(auth.getCurrentUser().getUid()).set(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            auth.signOut();
                            linearLayout.setVisibility(View.GONE);
                            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}
