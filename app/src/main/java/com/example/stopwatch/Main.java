package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnStop, btnReset;

    private Handler handler;
    private long startTime, timeInMilliseconds, timeSwapBuff, updateTime = 0L;
    private Runnable updateTimerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.textView);
        btnStart = findViewById(R.id.button);
        btnStop = findViewById(R.id.button2);
        btnReset = findViewById(R.id.button3);

        handler = new Handler();

        updateTimerThread = new Runnable() {
            public void run() {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updateTime = timeSwapBuff + timeInMilliseconds;
                int secs = (int) (updateTime / 1000);
                int mins = secs / 60;
                int hrs = mins / 60;
                secs = secs % 60;
                int milliseconds = (int) (updateTime % 1000);
                tvTimer.setText("" + String.format("%02d", hrs) + ":"
                        + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
                handler.postDelayed(this, 0);
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff += timeInMilliseconds;
                handler.removeCallbacks(updateTimerThread);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff += timeInMilliseconds;
                handler.removeCallbacks(updateTimerThread);
                startTime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updateTime = 0L;
                tvTimer.setText("00:00:00:000");
            }
        });
    }
    public void closeApp(View view) {
        finish(); // Closes the activity
    }

}

