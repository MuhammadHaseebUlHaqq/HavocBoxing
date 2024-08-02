package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityShadowboxing5Binding;

public class ShadowBoxing5 extends AppCompatActivity {

    private ActivityShadowboxing5Binding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInShadowBoxing5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShadowboxing5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setListener();

        // Set up the VideoView
        VideoView videoView = binding.videoView5;
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.up);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        binding.timerText5.setText("00:30");
        binding.pauseButton5.setText("PAUSE");

        startTimer(timeRemaining);

        binding.pauseButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTimer(timeRemaining);
                    binding.pauseButton5.setText("PAUSE");
                    isPaused = false;
                    videoView.start(); // Resume video
                } else {
                    countDownTimer.cancel();
                    binding.pauseButton5.setText("RESUME");
                    isPaused = true;
                    videoView.pause(); // Pause video
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInShadowBoxing5 = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInShadowBoxing5 = false;
    }

    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                binding.timerText5.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (isInShadowBoxing5) {
                    Intent intent = new Intent(ShadowBoxing5.this, RestActivity5.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
    private void setListener(){
        binding.nextactivity.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), RestActivity5.class));});
    }
}
