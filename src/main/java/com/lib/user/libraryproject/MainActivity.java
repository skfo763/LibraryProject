package com.lib.user.libraryproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout gouri;
    private Button checkSeat, checkSeat2;
    private TextView seatinfo, seatname, seattime, seatwarning;
    private TextView spaceinfo, spacename, spacetime, spacewarning;
    private Button checkSpace;
    private ImageView profile;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user_data;
    ArrayList<Map<String, Object>> seat_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user_data = documentSnapshot.getData();
            }
        });

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("발권좌석").limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        seat_data.add(snapshot.getData());
                    }

                    if (!seat_data.isEmpty()) {
                        Long time = System.currentTimeMillis() - Long.parseLong(seat_data.get(0).get("firsttime").toString());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String getTime = sdf.format(time);

                        seatinfo.setText(seat_data.get(0).get("univ").toString().concat(" ").concat(seat_data.get(0).get("cat").toString().concat(" "))
                                .concat(seat_data.get(0).get("chairnum").toString().concat("번 좌석")));
                        seatname.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        seattime.setText(getTime);
                        seatwarning.setText(String.valueOf(
                                Integer.parseInt(seat_data.get(0).get("alertNoise").toString())
                                        + Integer.parseInt(seat_data.get(0).get("alertPriv").toString())
                                        + Integer.parseInt(seat_data.get(0).get("alertIlegal").toString())));
                    }
                }
            }
        });

        profile = findViewById(R.id.main_profile);
        gouri = findViewById(R.id.main_url);
        checkSeat = findViewById(R.id.checkseat);
        checkSpace = findViewById(R.id.checkspace);
        seatinfo = findViewById(R.id.main_seat_info);
        seatname = findViewById(R.id.main_seat_name);
        seattime = findViewById(R.id.main_seat_remaintime);
        seatwarning = findViewById(R.id.main_seat_alert);
        spaceinfo = findViewById(R.id.main_space_info);
        spacename = findViewById(R.id.main_space_name);
        spacetime = findViewById(R.id.main_space_remaintime);
        spacewarning = findViewById(R.id.main_space_alert);
        checkSeat2 = findViewById(R.id.checkseat2);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gouri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if(user_data.isEmpty()) {
                    Toast.makeText(MainActivity.this, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if(user_data.get("university").equals("성균관대학교")) {
                        url = "https://lib.skku.edu/";
                    } else if(user_data.get("university").equals("서울과학기술대학교")) {
                        url = "https://library.seoultech.ac.kr/";
                    } else if(user_data.get("university").equals("서울여자대학교")) {
                        url = "http://lib.swu.ac.kr/";
                    } else if(user_data.get("university").equals("한성대학교")) {
                        url = "http://hsel.hansung.ac.kr/";
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

        checkSeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"성균관대학교", "서울과학기술대학교", "서울여자대학교"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("학교 선택")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CheckSeatActivity.class);
                                intent.putExtra("university", items[which]);
                                intent.putExtra("my_univ", user_data.get("university").toString());
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        checkSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"성균관대학교", "서울과학기술대학교", "서울여자대학교"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("학교 선택")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CheckSeatActivity.class);
                                intent.putExtra("university", items[which]);
                                intent.putExtra("my_univ", user_data.get("university").toString());
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        checkSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String items[] = {"성균관대학교", "서울과학기술대학교", "서울여자대학교"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("학교 선택")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CheckSeatActivity.class);
                                intent.putExtra("university", items[which]);
                                intent.putExtra("my_univ", user_data.get("university").toString());
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }
}
