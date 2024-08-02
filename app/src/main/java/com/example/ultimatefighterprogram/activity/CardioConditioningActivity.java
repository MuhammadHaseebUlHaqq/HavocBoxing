package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityCardioConditioningBinding;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;

public class CardioConditioningActivity extends AppCompatActivity {

    private ActivityCardioConditioningBinding binding;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private long timeRemaining = 30000; // 30 seconds
    private boolean isInWorkout = false;

    private PreferenceManager preferenceManager;

    private static final String[] CARDIO_CONDITIONING_WORKOUTS = {
            "Jumping Jacks", "High Knees", "Flutter Kicks", "Foot Circles",
            "In and Out", "Neck Rolls","Ropes"
    };
    private static final int[] CARDIO_CONDITIONING_VIDEOS = {
            R.raw.jumpingjacks, R.raw.lunges, R.raw.flutterkicks, R.raw.footcircles,
            R.raw.boathold, R.raw.neckrolls, R.raw.ropes
    };

    private int workoutIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardioConditioningBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferenceManager = new PreferenceManager(getApplicationContext());

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
        binding.exerciseName.setText(CARDIO_CONDITIONING_WORKOUTS[workoutIndex]);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + CARDIO_CONDITIONING_VIDEOS[workoutIndex]);
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
                    if (workoutIndex == CARDIO_CONDITIONING_WORKOUTS.length - 1) {
                        // Last workout completed, update workout details
                        updateWorkoutData(7);
                    }
                    Intent intent = new Intent(CardioConditioningActivity.this, RestCardioConditioningActivity.class);
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
