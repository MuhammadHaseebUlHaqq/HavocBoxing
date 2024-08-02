package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityShadowboxing1Binding;

public class ShadowBoxing1 extends AppCompatActivity {

    private ActivityShadowboxing1Binding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInShadowBoxing1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShadowboxing1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setListener();
        setContentView(view);

        // Set up the VideoView
        VideoView videoView = binding.videoView;
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.jab);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        binding.timerText.setText("00:30");
        binding.pauseButton.setText("PAUSE");

        startTimer(timeRemaining);

        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTimer(timeRemaining);
                    binding.pauseButton.setText("PAUSE");
                    isPaused = false;
                    videoView.start(); // Resume video
                } else {
                    countDownTimer.cancel();
                    binding.pauseButton.setText("RESUME");
                    isPaused = true;
                    videoView.pause(); // Pause video
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInShadowBoxing1 = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInShadowBoxing1 = false;
    }

    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                binding.timerText.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (isInShadowBoxing1) {
                    Intent intent = new Intent(ShadowBoxing1.this, RestActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
    private void setListener(){
        binding.nextactivity.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), RestActivity.class));});
    }
}