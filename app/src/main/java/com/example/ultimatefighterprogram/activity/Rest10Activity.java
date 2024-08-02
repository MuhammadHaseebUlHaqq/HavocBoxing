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

public class Rest10Activity extends AppCompatActivity {

    private TextView restTimerText;
    private Button skipButton;
    private VideoView videoView;
    private CountDownTimer restCountDownTimer;
    private boolean isActivityInForeground = false; // Flag to check if the activity is in the foreground

    private static final String[] NEXT_WORKOUTS = {
            "NEXT: Jab-Hook-Hook", "NEXT: Jab-Cross-Lean", "NEXT: Jab-Cross-Duck-Cross",
            "NEXT: Jab-Uppercut", "NEXT: Jab-Cross-Hook-Cross", "NEXT: Jab-Lean-Hook"
    };

    private int workoutIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restt);

        workoutIndex = getIntent().getIntExtra("workoutIndex", 0);

        restTimerText = findViewById(R.id.restTimertext);
        skipButton = findViewById(R.id.skipbtn);
        videoView = findViewById(R.id.videoView1);

        TextView rest1 = findViewById(R.id.rest1);
        rest1.setText(NEXT_WORKOUTS[workoutIndex]);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rest);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        startRestTimer(10000); // 10 seconds

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

    @Override
    protected void onResume() {
        super.onResume();
        isActivityInForeground = true; // Activity is in the foreground
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityInForeground = false; // Activity is no longer in the foreground
    }

    private void startRestTimer(long time) {
        restCountDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                restTimerText.setText("00:" + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                if (isActivityInForeground) { // Check if the activity is still in the foreground
                    startNextExercise();
                }
            }
        }.start();
    }

    private void startNextExercise() {
        if (workoutIndex < 6) {
            workoutIndex++;
            Intent intent = new Intent(Rest10Activity.this, WorkoutActivity.class);
            intent.putExtra("workoutIndex", workoutIndex);
            startActivity(intent);
            finish();
        } else {
            // Handle the end of the workout session here
        }
    }
}
