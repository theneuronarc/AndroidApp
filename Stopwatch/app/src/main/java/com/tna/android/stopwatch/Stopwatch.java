package com.tna.android.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Stopwatch extends AppCompatActivity {

    private int seconds = 0;
    private Boolean isRunning = false;

    private void printCurrentTime(int seconds){
        TextView timeView = (TextView)findViewById(R.id.time_view);
        int hours = seconds/3600;
        int minutes = (seconds%3600)/60;
        int secs = seconds%60;
        String time = String.format("%d:%02d:%02d",hours, minutes, secs);
        timeView.setText(time);

    }

    private void runTimer(){
        printCurrentTime(seconds);
        if(isRunning)
            seconds++;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runTimer();
            }
        }, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                runTimer();
            }
        });
    }

    protected void onClickStart(View view){
        isRunning = true;
    }

    protected void onClickStop(View view){
        isRunning = false;
    }

    protected void onClickReset(View view){
        isRunning = false;
        seconds = 0;
    }
}
