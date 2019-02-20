package com.lib.user.libraryproject;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowRdRoomActivity extends AppCompatActivity {

    private String[] content_data = {"배정", "신고", "닫기"};
    private String[] alert_data = {"소음 : 시끄럽게 떠들거나 일정 수위 이상의 소음을 유발하는 행위", "사석화 : 짐을 가져가지 않고 두고 다니는 행위 등", "불법점유 : 발권시간이 지났는데 좌석을 점유하는 행위"};
    private String univ, cat;
    private LinearLayout linearLayout;
    private Button[] buttons = new Button[512];
    private int[] buttonId = {0, R.id.rdroom_btn1, R.id.rdroom_btn2, R.id.rdroom_btn3, R.id.rdroom_btn4, R.id.rdroom_btn5, R.id.rdroom_btn6, R.id.rdroom_btn7, R.id.rdroom_btn8, R.id.rdroom_btn9, R.id.rdroom_btn10,
            R.id.rdroom_btn11, R.id.rdroom_btn12, R.id.rdroom_btn13, R.id.rdroom_btn14, R.id.rdroom_btn15, R.id.rdroom_btn16, R.id.rdroom_btn17, R.id.rdroom_btn18, R.id.rdroom_btn19, R.id.rdroom_btn20,
            R.id.rdroom_btn21, R.id.rdroom_btn22, R.id.rdroom_btn23, R.id.rdroom_btn24, R.id.rdroom_btn25, R.id.rdroom_btn26, R.id.rdroom_btn27, R.id.rdroom_btn28, R.id.rdroom_btn29, R.id.rdroom_btn30,
            R.id.rdroom_btn31, R.id.rdroom_btn32, R.id.rdroom_btn33, R.id.rdroom_btn34, R.id.rdroom_btn35, R.id.rdroom_btn36, R.id.rdroom_btn37, R.id.rdroom_btn38, R.id.rdroom_btn39, R.id.rdroom_btn40,
            R.id.rdroom_btn41, R.id.rdroom_btn42, R.id.rdroom_btn43, R.id.rdroom_btn44, R.id.rdroom_btn45, R.id.rdroom_btn46, R.id.rdroom_btn47, R.id.rdroom_btn48, R.id.rdroom_btn49, R.id.rdroom_btn50,
            R.id.rdroom_btn51, R.id.rdroom_btn52, R.id.rdroom_btn53, R.id.rdroom_btn54, R.id.rdroom_btn55, R.id.rdroom_btn56, R.id.rdroom_btn57, R.id.rdroom_btn58, R.id.rdroom_btn59, R.id.rdroom_btn60,
            R.id.rdroom_btn61, R.id.rdroom_btn62, R.id.rdroom_btn63, R.id.rdroom_btn64, R.id.rdroom_btn65, R.id.rdroom_btn66, R.id.rdroom_btn67, R.id.rdroom_btn68, R.id.rdroom_btn69, R.id.rdroom_btn70,
            R.id.rdroom_btn71, R.id.rdroom_btn72, R.id.rdroom_btn73, R.id.rdroom_btn74, R.id.rdroom_btn75, R.id.rdroom_btn76, R.id.rdroom_btn77, R.id.rdroom_btn78, R.id.rdroom_btn79, R.id.rdroom_btn80,
            R.id.rdroom_btn81, R.id.rdroom_btn82, R.id.rdroom_btn83, R.id.rdroom_btn84, R.id.rdroom_btn85, R.id.rdroom_btn86, R.id.rdroom_btn87, R.id.rdroom_btn88, R.id.rdroom_btn89, R.id.rdroom_btn90,
            R.id.rdroom_btn91, R.id.rdroom_btn92, R.id.rdroom_btn93, R.id.rdroom_btn94, R.id.rdroom_btn95, R.id.rdroom_btn96, R.id.rdroom_btn97, R.id.rdroom_btn98, R.id.rdroom_btn99, R.id.rdroom_btn100,
            R.id.rdroom_btn101, R.id.rdroom_btn102, R.id.rdroom_btn103, R.id.rdroom_btn104, R.id.rdroom_btn105, R.id.rdroom_btn106, R.id.rdroom_btn107, R.id.rdroom_btn108, R.id.rdroom_btn109, R.id.rdroom_btn110,
            R.id.rdroom_btn111, R.id.rdroom_btn112, R.id.rdroom_btn113, R.id.rdroom_btn114, R.id.rdroom_btn115, R.id.rdroom_btn116, R.id.rdroom_btn117, R.id.rdroom_btn118, R.id.rdroom_btn119, R.id.rdroom_btn120,
            R.id.rdroom_btn121, R.id.rdroom_btn122, R.id.rdroom_btn123, R.id.rdroom_btn124, R.id.rdroom_btn125, R.id.rdroom_btn126, R.id.rdroom_btn127, R.id.rdroom_btn128, R.id.rdroom_btn129, R.id.rdroom_btn130,
            R.id.rdroom_btn131, R.id.rdroom_btn132, R.id.rdroom_btn133, R.id.rdroom_btn134, R.id.rdroom_btn135, R.id.rdroom_btn136, R.id.rdroom_btn137, R.id.rdroom_btn138, R.id.rdroom_btn139, R.id.rdroom_btn140,
            R.id.rdroom_btn141, R.id.rdroom_btn142, R.id.rdroom_btn143, R.id.rdroom_btn144, R.id.rdroom_btn145, R.id.rdroom_btn146, R.id.rdroom_btn147, R.id.rdroom_btn148, R.id.rdroom_btn149, R.id.rdroom_btn150,
            R.id.rdroom_btn151, R.id.rdroom_btn152, R.id.rdroom_btn153, R.id.rdroom_btn154, R.id.rdroom_btn155, R.id.rdroom_btn156, R.id.rdroom_btn157, R.id.rdroom_btn158, R.id.rdroom_btn159, R.id.rdroom_btn160,
            R.id.rdroom_btn161, R.id.rdroom_btn162, R.id.rdroom_btn163, R.id.rdroom_btn164, R.id.rdroom_btn165, R.id.rdroom_btn166, R.id.rdroom_btn167, R.id.rdroom_btn168, R.id.rdroom_btn169, R.id.rdroom_btn170,
            R.id.rdroom_btn171, R.id.rdroom_btn172, R.id.rdroom_btn173, R.id.rdroom_btn174, R.id.rdroom_btn175, R.id.rdroom_btn176, R.id.rdroom_btn177, R.id.rdroom_btn178, R.id.rdroom_btn179, R.id.rdroom_btn180,
            R.id.rdroom_btn181, R.id.rdroom_btn182, R.id.rdroom_btn183, R.id.rdroom_btn184, R.id.rdroom_btn185, R.id.rdroom_btn186, R.id.rdroom_btn187, R.id.rdroom_btn188, R.id.rdroom_btn189, R.id.rdroom_btn190,
            R.id.rdroom_btn191, R.id.rdroom_btn192, R.id.rdroom_btn193, R.id.rdroom_btn194, R.id.rdroom_btn195, R.id.rdroom_btn196, R.id.rdroom_btn197, R.id.rdroom_btn198, R.id.rdroom_btn199, R.id.rdroom_btn200,
            R.id.rdroom_btn201, R.id.rdroom_btn202, R.id.rdroom_btn203, R.id.rdroom_btn204, R.id.rdroom_btn205, R.id.rdroom_btn206, R.id.rdroom_btn207, R.id.rdroom_btn208, R.id.rdroom_btn209, R.id.rdroom_btn210,
            R.id.rdroom_btn211, R.id.rdroom_btn212, R.id.rdroom_btn213, R.id.rdroom_btn214, R.id.rdroom_btn215, R.id.rdroom_btn216, R.id.rdroom_btn217, R.id.rdroom_btn218, R.id.rdroom_btn219, R.id.rdroom_btn220,
            R.id.rdroom_btn221, R.id.rdroom_btn222, R.id.rdroom_btn223, R.id.rdroom_btn224, R.id.rdroom_btn225, R.id.rdroom_btn226, R.id.rdroom_btn227, R.id.rdroom_btn228, R.id.rdroom_btn229, R.id.rdroom_btn230,
            R.id.rdroom_btn231, R.id.rdroom_btn232, R.id.rdroom_btn233, R.id.rdroom_btn234, R.id.rdroom_btn235, R.id.rdroom_btn236, R.id.rdroom_btn237, R.id.rdroom_btn238, R.id.rdroom_btn239, R.id.rdroom_btn240,
            R.id.rdroom_btn241, R.id.rdroom_btn242, R.id.rdroom_btn243, R.id.rdroom_btn244, R.id.rdroom_btn245, R.id.rdroom_btn246, R.id.rdroom_btn247, R.id.rdroom_btn248
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rd_room);
        univ = getIntent().getStringExtra("univ");
        cat = getIntent().getStringExtra("cat");
        linearLayout = findViewById(R.id.progress_layout);
        linearLayout.setVisibility(View.VISIBLE);

        buttons[0] = null;
        for(int i=0; i<buttonId.length; i++) {
            if(i != 0) {
                buttons[i] = findViewById(buttonId[i]);
                final String num = String.valueOf(i);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowRdRoomActivity.this);
                        builder.setTitle("좌석번호 : ".concat(num))
                                .setItems(content_data, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0) {
                                            linearLayout.setVisibility(View.VISIBLE);
                                            FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록").document("좌석".concat(num))
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        if(task.getResult().exists()) {
                                                            String check = task.getResult().get("processed").toString();
                                                            if(check.equals("1")) {
                                                                Toast.makeText(ShowRdRoomActivity.this, "이미 발권된 좌석입니다.", Toast.LENGTH_SHORT).show();
                                                                linearLayout.setVisibility(View.GONE);
                                                            } else {
                                                                assignSeat(Integer.parseInt(num));
                                                            }
                                                        } else {
                                                            assignSeat(Integer.parseInt(num));
                                                        }
                                                    }
                                                }
                                            });
                                        } else if(which == 1) {
                                            linearLayout.setVisibility(View.VISIBLE);
                                            FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록").document("좌석".concat(num))
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        if(task.getResult().exists()) {
                                                            String check = task.getResult().get("processed").toString();
                                                            String user = task.getResult().get("user").toString();
                                                            if(check.equals("1")) {
                                                                sendAlert(Integer.parseInt(num), user);
                                                            } else {
                                                                Toast.makeText(ShowRdRoomActivity.this, "해당 좌석을 아무도 발권하지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                                linearLayout.setVisibility(View.GONE);
                                                            }
                                                        } else {
                                                            Toast.makeText(ShowRdRoomActivity.this, "해당 좌석을 아무도 발권하지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                            linearLayout.setVisibility(View.GONE);
                                                        }
                                                    }
                                                }
                                            });
                                        } else if(which == 2) {
                                            dialog.dismiss();
                                            linearLayout.setVisibility(View.GONE);
                                        }
                                    }
                                }).show();
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }
        }

        FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot snapshot : task.getResult()) {
                        if(snapshot.exists()) {
                            String check = snapshot.getData().get("processed").toString();
                            int index = Integer.parseInt(snapshot.getData().get("chairnum").toString());
                            if(check.equals("1")) {
                                buttons[index].setBackgroundColor(0xFFFF9800);
                            }
                        }
                    }
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void assignSeat(final int index) {
        Map<String, Object> input_data = new HashMap<>();
        input_data.put("chairnum", index);
        input_data.put("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
        input_data.put("firstTime", System.currentTimeMillis());
        input_data.put("alertNoise", "0");
        input_data.put("alertPriv", "0");
        input_data.put("alertIlegal", "0");
        input_data.put("processed", "1");
        FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록").document("좌석".concat(String.valueOf(index))).set(input_data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Map<String, Object> ddd = new HashMap<>();
                            ddd.put("univ", univ);
                            ddd.put("cat", cat);
                            ddd.put("chairnum", index);
                            ddd.put("firsttime", SystemClock.currentThreadTimeMillis());
                            ddd.put("alertNoise", "0");
                            ddd.put("alertPriv", "0");
                            ddd.put("alertIlegal", "0");
                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("발권좌석").add(ddd)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(ShowRdRoomActivity.this, "좌석 배정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                buttons[index].setBackgroundColor(0xFFFFE079);
                                                linearLayout.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(ShowRdRoomActivity.this, "좌석배정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                linearLayout.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(ShowRdRoomActivity.this, "좌석배정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void sendAlert(final int index, String s) {
        System.out.println(s);
        final String[] token = new String[1];
        FirebaseFirestore.getInstance().collection("users").document(s).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        token[0] = task.getResult().get("pushToken").toString();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowRdRoomActivity.this);
                        builder1.setTitle("신고할 좌석번호 : ".concat(String.valueOf(index)))
                                .setItems(alert_data, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0) {
                                            String title = "소음신고가 접수되었습니다.";
                                            String context = "해당 좌석에 소음신고가 접수되었습니다. 열람실 이용시 정숙해주시기 바랍니다. 신고가 9회 누적접수될 시 열람실 이용이 제한될 수 있습니다.";
                                            Alert2DB("alertNoise", index);
                                            sendFCM(title, context, token[0]);
                                        } else if(which == 1) {
                                            String title = "사석화 신고가 접수되었습니다.";
                                            String context = "해당 좌석에 사석화 신고가 접수되었습니다. 공용좌석 사용에 각별히 유의해주시기 바랍니다. 신고가 9회 누적접수될 시 열람실 이용이 제한될 수 있습니다.";
                                            Alert2DB("alertPriv", index);
                                            Toast.makeText(ShowRdRoomActivity.this, "사석화 신고접수가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                            sendFCM(title, context, token[0]);
                                        } else if (which == 2) {
                                            String title = "불법점유 신고가 접수되었습니다.";
                                            String context = "해당 좌석에 불법점유 신고가 접수되었습니다. 발권 후 좌석사용해주시기 바랍니다. 신고가 9회 누적접수될 시 열람실 이용이 제한될 수 있습니다.";
                                            Alert2DB("AlertIlegal", index);
                                            sendFCM(title, context, token[0]);
                                        }
                                    }
                                }).show();
                    }
                }
            }
        });
    }

    private void Alert2DB(final String alert, final int index) {
        FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록").document("좌석".concat(String.valueOf(index)))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                int count = Integer.parseInt(task.getResult().get(alert).toString()) + 1;
                Map<String, Object> dt = new HashMap<>();
                dt.put(alert, count);
                FirebaseFirestore.getInstance().collection(univ).document(cat).collection("좌석목록").document("좌석".concat(String.valueOf(index)))
                        .update(dt).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ShowRdRoomActivity.this, "신고접수가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            buttons[index].setBackgroundColor(0xFFFF6161);
                            linearLayout.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ShowRdRoomActivity.this, "신고접수에 실패했습니다", Toast.LENGTH_SHORT).show();
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void sendFCM(String title, String context, String token) {
        Gson gson = new Gson();
        NotificationModels notification = new NotificationModels();
        notification.data.text = context;
        notification.data.title = title;
        notification.to = token;

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notification));
        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AIzaSyDGVgx4p1uHs0zDvK3bD2jqAy-QIitm_KI")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
