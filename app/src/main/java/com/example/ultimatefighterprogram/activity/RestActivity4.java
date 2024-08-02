package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityRest4Binding;

public class RestActivity4 extends AppCompatActivity {

    private ActivityRest4Binding binding;
    private CountDownTimer restCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding
        binding = ActivityRest4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set up the VideoView
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rest); // Replace 'boxing' with your actual video file name
        binding.videoView1.setVideoURI(videoUri);
        binding.videoView1.start();
        binding.videoView1.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        startRestTimer(10000); // 10 seconds

        binding.skipbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restCountDownTimer != null) {
                    restCountDownTimer.cancel();
                }
                startNextExercise();
            }
        });
    }

    private void startRestTimer(long time) {
        restCountDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.restTimertext4.setText("00:" + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                startNextExercise();
            }
        }.start();
    }

    private void startNextExercise() {
        Intent intent = new Intent(RestActivity4.this, ShadowBoxing5.class); // Replace 'MainActivity' with your next activity
        startActivity(intent);
        finish();
    }
}

