package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityStrengthTrainingBinding;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StrengthTrainingActivity extends AppCompatActivity {

    private ActivityStrengthTrainingBinding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInWorkout = false;

    private static final String[] STRENGTH_WORKOUTS = {
            "Push-Ups", "Squats", "Lunges", "Boat Hold",
            "Jumping Jacks", "Sit-Ups", "Push-Ups"
    };
    private static final int[] STRENGTH_VIDEOS = {
            R.raw.pushups, R.raw.squats, R.raw.lunges, R.raw.boathold,
            R.raw.jumpingjacks, R.raw.squats, R.raw.pushups
    };

    private int workoutIndex = 0;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStrengthTrainingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferenceManager = new PreferenceManager(this);

        workoutIndex = getIntent().getIntExtra("workoutIndex", 0);
        setUpWorkout();

        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    startTimer(timeRemaining);
                    binding.pauseButton.setText("PAUSE");
                    isPaused = false;
                    binding.videoView.start(); // Resume video
                } else {
                    countDownTimer.cancel();
                    binding.pauseButton.setText("RESUME");
                    isPaused = true;
                    binding.videoView.pause(); // Pause video
                }
            }
        });
    }

    private void setUpWorkout() {
        binding.exerciseName.setText(STRENGTH_WORKOUTS[workoutIndex]);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + STRENGTH_VIDEOS[workoutIndex]);
        binding.videoView.setVideoURI(videoUri);
        binding.videoView.start();
        binding.videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video

        binding.timerText.setText("00:30");
        binding.pauseButton.setText("PAUSE");

        startTimer(timeRemaining);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInWorkout = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInWorkout = false;
    }

    private void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                binding.timerText.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (isInWorkout) {
                    if (workoutIndex == STRENGTH_WORKOUTS.length - 1) {
                        // Last workout completed, update workout details
                        updateWorkoutData(7);
                    }
                    Intent intent = new Intent(StrengthTrainingActivity.this, RestStrengthTrainingActivity.class);
                    intent.putExtra("workoutIndex", workoutIndex);
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
}
