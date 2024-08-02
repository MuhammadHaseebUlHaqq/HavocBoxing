package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityShadowboxing7Binding;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;

public class ShadowBoxing7 extends AppCompatActivity {

    private ActivityShadowboxing7Binding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInShadowBoxing7 = false;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShadowboxing7Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferenceManager = new PreferenceManager(getApplicationContext());
        setListener();

        // Set up the VideoView
        VideoView videoView = binding.videoView7;
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.jabcross);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        binding.timerText7.setText("00:30");
        binding.pauseButton7.setText("PAUSE");

        startTimer(timeRemaining);

        binding.pauseButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTimer(timeRemaining);
                    binding.pauseButton7.setText("PAUSE");
                    isPaused = false;
                    videoView.start(); // Resume video
                } else {
                    countDownTimer.cancel();
                    binding.pauseButton7.setText("RESUME");
                    isPaused = true;
                    videoView.pause(); // Pause video
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInShadowBoxing7 = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInShadowBoxing7 = false;
    }

    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                binding.timerText7.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (isInShadowBoxing7) {
                    updateWorkoutData(7); // Assuming the workout duration is 30 minutes
                    Intent intent = new Intent(ShadowBoxing7.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    private void updateWorkoutData(int workoutDuration) {
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);
        SharedPreferences sharedPreferences = getSharedPreferences("workout_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String dailyWorkoutsKey = userId + "_daily_workouts";
        String totalWorkoutsKey = userId + "_total_workouts";
        String totalMinutesKey = userId + "_total_minutes";
        String coinsKey = userId + "_coins";

        int dailyWorkouts = sharedPreferences.getInt(dailyWorkoutsKey, 0) + 1;
        int totalWorkouts = sharedPreferences.getInt(totalWorkoutsKey, 0) + 1;
        int totalMinutes = sharedPreferences.getInt(totalMinutesKey, 0) + workoutDuration;
        int coins = sharedPreferences.getInt(coinsKey, 0) + 10;

        editor.putInt(dailyWorkoutsKey, dailyWorkouts);
        editor.putInt(totalWorkoutsKey, totalWorkouts);
        editor.putInt(totalMinutesKey, totalMinutes);
        editor.putInt(coinsKey, coins);
        editor.apply();
    }
    private void setListener(){
        binding.nextactivity.setOnClickListener(v -> {startActivity(new Intent(getApplicationContext(), MainActivity.class));});
        updateWorkoutData(7);
    }
}
