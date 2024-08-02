package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityShadowboxing6Binding;

public class ShadowBoxing6 extends AppCompatActivity {

    private ActivityShadowboxing6Binding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInShadowBoxing6 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShadowboxing6Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setListener();

        // Set up the VideoView
        VideoView videoView = binding.videoView6;
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.up);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        binding.timerText6.setText("00:30");
        binding.pauseButton6.setText("PAUSE");

        startTimer(timeRemaining);

        binding.pauseButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTimer(timeRemaining);
                    binding.pauseButton6.setText("PAUSE");
                    isPaused = false;
                    videoView.start(); // Resume video
                } else {
                    countDownTimer.cancel();
                    binding.pauseButton6.setText("RESUME");
                    isPaused = true;
                    videoView.pause(); // Pause video
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInShadowBoxing6 = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInShadowBoxing6 = false;
    }

    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                binding.timerText6.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (isInShadowBoxing6) {
                    Intent intent = new Intent(ShadowBoxing6.this, RestActivity6.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
    private void setListener(){
        binding.nextactivity.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), RestActivity6.class));});
    }
}
