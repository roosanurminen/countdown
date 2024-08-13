package com.example.countdowntimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CountdownPage extends Activity {
    private TextView titleView;
    private TextView dateView;
    private TextView daysView;
    private TextView hoursView;
    private TextView minutesView;
    private TextView secondsView;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        titleView = findViewById(R.id.event_title);
        dateView = findViewById(R.id.date_textView);
        daysView = findViewById(R.id.days);
        hoursView = findViewById(R.id.hours);
        minutesView = findViewById(R.id.minutes);
        secondsView = findViewById(R.id.seconds);
        deleteButton = findViewById(R.id.delete_btn);

        String title = getIntent().getStringExtra("TITLE");
        String date = getIntent().getStringExtra("DATE");

        titleView.setText(title);
        dateView.setText(date);


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date givenDate;
        try {
            givenDate = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        assert givenDate != null;
        long millis = givenDate.getTime() - System.currentTimeMillis();

        new CountDownTimer(millis, 1000) {
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                seconds %= 60;
                minutes %= 60;
                hours %= 24;

                daysView.setText(String.valueOf(days));
                hoursView.setText(String.format("%02d", hours));
                minutesView.setText(String.format("%02d", minutes));
                secondsView.setText(String.format("%02d", seconds));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                daysView.setText("0");
                hoursView.setText("00");
                minutesView.setText("00");
                secondsView.setText("00");
            }
        }.start();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonHandler.deleteDataFromJson(CountdownPage.this, title, date);
                finish();
            }
        });
    }
}