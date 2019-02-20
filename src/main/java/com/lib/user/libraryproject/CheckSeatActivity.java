package com.lib.user.libraryproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CheckSeatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String category, sub_cat;
    private ArrayList<Map<String, Object>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_seat);
        recyclerView = findViewById(R.id.checkseat_recyclerview);
        category = getIntent().getStringExtra("university");
        sub_cat = getIntent().getStringExtra("my_univ");

        check();

        if(category.equals("성균관대학교")) {
            FirebaseFirestore.getInstance().collection("성균관대학교").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(DocumentSnapshot snapshot : task.getResult()) {
                            String temp = snapshot.getId();
                            Map<String, Object> t_data = snapshot.getData();
                            t_data.put("id", temp);
                            data.add(t_data);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(CheckSeatActivity.this));
                        recyclerView.setAdapter(new MyAdapter(data));
                    }
                }
            });
        } else if(category.equals("서울과학기술대학교")) {
            FirebaseFirestore.getInstance().collection("서울과학기술대학교").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(DocumentSnapshot snapshot : task.getResult()) {
                            String temp = snapshot.getId();
                            Map<String, Object> t_data = snapshot.getData();
                            t_data.put("id", temp);
                            data.add(t_data);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(CheckSeatActivity.this));
                        recyclerView.setAdapter(new MyAdapter(data));
                    }
                }
            });
        } else if(category.equals("서울여자대학교")) {
            FirebaseFirestore.getInstance().collection("서울여자대학교").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(DocumentSnapshot snapshot : task.getResult()) {
                            String temp = snapshot.getId();
                            Map<String, Object> t_data = snapshot.getData();
                            t_data.put("id", temp);
                            data.add(t_data);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(CheckSeatActivity.this));
                        recyclerView.setAdapter(new MyAdapter(data));
                    }
                }
            });
        }
    }

    private void check() {
        if(!category.equals(sub_cat)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CheckSeatActivity.this);
            builder.setTitle("타대학 열람실 발권 안내")
                    .setMessage("타대학 도서관의 열람실 좌석을 발권하기 위해서는, 별도의 인증 절차가 필요합니다. 각 대학의 DB접근 및 개인정보 보호를 위해 반드시 필요한 절차이오니," +
                            "반드시 인증절차를 거쳐 주시기 바랍니다. 인증번호를 타대학 좌석발권기에 입력해주시기 바랍니다. 인증번호 미입력 시 발권이 취소됩니다." +
                            "인증번호 : 127436")
                    .show();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter {
        ArrayList<Map<String, Object>> dt;
        public MyAdapter(ArrayList<Map<String, Object>> data) {
            this.dt = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_readingroom, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            ((CustomViewHolder)holder).textView.setText(dt.get(position).get("id").toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CheckSeatActivity.this, ShowRdRoomActivity.class);
                    intent.putExtra("univ", category);
                    intent.putExtra("cat", dt.get(position).get("id").toString());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CustomViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.rdroom_textview);
        }
    }
}
