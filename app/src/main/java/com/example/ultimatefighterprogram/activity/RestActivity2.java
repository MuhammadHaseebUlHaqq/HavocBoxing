package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;

public class RestActivity2 extends AppCompatActivity {

    private TextView restTimerText;
    private Button skipButton;
    private VideoView videoView;
    private CountDownTimer restCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest2);

        restTimerText = findViewById(R.id.restTimertext2);
        skipButton = findViewById(R.id.skipbtn2);
        videoView = findViewById(R.id.videoView1);

        // Set up the VideoView
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rest); // Replace 'boxing' with your actual video file name
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        if (restTimerText != null) {
            startRestTimer(10000); // 10 seconds
        }

        if (skipButton != null) {
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (restCountDownTimer != null) {
                        restCountDownTimer.cancel();
                    }
                    startNextExercise();
                }
            });
        }
    }

    private void startRestTimer(long time) {
        restCountDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                restTimerText.setText("00:" + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                startNextExercise();
            }
        }.start();
    }

    private void startNextExercise() {
        Intent intent = new Intent(RestActivity2.this, ShadowBoxing3.class); // Replace 'MainActivity' with your next activity
        startActivity(intent);
        finish();
    }
}
