package com.example.task41p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    TextView lastTimeText;
    TextView timerText;
    TextView taskTypeText;
    EditText taskTypeEditText;
    ImageButton startImageButton;
    ImageButton pauseImageButton;
    ImageButton stopImageButton;
    Timer timer;
    long seconds;
    Handler handler;
    NumberFormat timeFormatter;
    SharedPreferences sharedPreferences;
    String lastTimeKey = "lastTimeKey";
    String timerRunningKey = "timerRunningKey";
    String taskTypeTextKey = "taskTypeTextKey";
    String firstRunKey = "firstRunKey";
    boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastTimeText = findViewById(R.id.lastTimeText);
        timerText = findViewById(R.id.timerText);
        taskTypeText = findViewById(R.id.taskTypeText);
        taskTypeEditText = findViewById(R.id.taskTypeEditText);
        startImageButton = findViewById(R.id.startImageButton);
        pauseImageButton = findViewById(R.id.pauseImageButton);
        stopImageButton = findViewById(R.id.stopImageButton);
        handler = new Handler();
        timeFormatter = new DecimalFormat("00");
        sharedPreferences = getSharedPreferences("com.example.task41p", MODE_PRIVATE);

        boolean isRunning = false;

        DisplayLastTime(sharedPreferences.getLong(lastTimeKey, 0), sharedPreferences.getString(taskTypeTextKey, ""));

        if(savedInstanceState != null && !savedInstanceState.getBoolean(firstRunKey, true))
        {
            seconds = savedInstanceState.getLong(lastTimeKey, 0);
            isRunning = savedInstanceState.getBoolean(timerRunningKey, false);
        }

        timerText.setText(GetTimeText(seconds));

        if(isRunning)
        {
            Start();
        }

        startImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start();
            }
        });

        pauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pause();
            }
        });

        stopImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop();
            }
        });
    }

    private void DisplayLastTime(long lastSeconds, String lastTaskType)
    {
        lastTimeText.setText("You spent " + GetTimeText(lastSeconds) + " on " +  lastTaskType + "  last time");
    }

    private void Start()
    {
        if(timerRunning)
        {
            return;
        }
        Timer.run();
        timerRunning = true;
    }

    private void Pause()
    {
        PauseTime();
        timerRunning = false;
    }

    private void Stop()
    {
        DisplayLastTime(seconds, taskTypeEditText.getText().toString());
        PauseTime();
        SaveData();
        seconds = 0;
        timerText.setText(GetTimeText(seconds));
        timerRunning = false;
    }

    private void SaveData()
    {
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putLong(lastTimeKey, seconds);
        editor.putString(taskTypeTextKey, taskTypeEditText.getText().toString());
        editor.apply();
        timerRunning = false;
    }

    private void PauseTime()
    {
        handler.removeCallbacks(Timer);
    }

    private void IncreaseTime()
    {
        seconds++;
        timerText.setText(GetTimeText(seconds));
    }

    private String GetTimeText(long time)
    {
        int hour = (int)time / 3600;
        int minute = ((int)time % 3600) / 60;
        int second = (int)time % 60;
        return timeFormatter.format(hour) + ":" + timeFormatter.format(minute) + ":" + timeFormatter.format(second);
    }

    Runnable Timer = new Runnable() {
        @Override
        public void run() {
            try {
                IncreaseTime();
            }
            finally {
                handler.postDelayed(Timer, 1000);
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong(lastTimeKey, seconds);
        savedInstanceState.putBoolean(timerRunningKey, timerRunning);
        savedInstanceState.putString(taskTypeTextKey, taskTypeEditText.getText().toString());
        savedInstanceState.putBoolean(firstRunKey, false);
    }
}