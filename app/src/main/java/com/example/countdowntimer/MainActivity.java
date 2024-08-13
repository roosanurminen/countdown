package com.example.countdowntimer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    Button newCountdown;
    TextView noCountdowns;

    @SuppressLint("SetTextI18n")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CountdownData> countdownDataList = JsonHandler.getCountdownDataFromJson(this);
        adapter = new Adapter(this, countdownDataList);
        recyclerView.setAdapter(adapter);

        noCountdowns = findViewById(R.id.no_countdowns);
        if (countdownDataList.isEmpty()) {
            noCountdowns.setText("No countdowns!");
        }

        newCountdown = findViewById(R.id.new_countdown);
        newCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCountdownPage.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("UnsafeIntentLaunch")
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}