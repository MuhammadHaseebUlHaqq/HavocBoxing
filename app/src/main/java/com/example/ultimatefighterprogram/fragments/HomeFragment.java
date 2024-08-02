package com.example.ultimatefighterprogram.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ultimatefighterprogram.databinding.FragmentHomeBinding;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private PreferenceManager preferenceManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        preferenceManager = new PreferenceManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views and logic here
        updateUI();
    }

    private void updateUI() {
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("workout_prefs", getActivity().MODE_PRIVATE);

        String dailyWorkoutsKey = userId + "_daily_workouts";
        String totalWorkoutsKey = userId + "_total_workouts";
        String totalMinutesKey = userId + "_total_minutes";
        String coinsKey = userId + "_coins";

        int dailyWorkouts = sharedPreferences.getInt(dailyWorkoutsKey, 0);
        int totalWorkouts = sharedPreferences.getInt(totalWorkoutsKey, 0);
        int totalMinutes = sharedPreferences.getInt(totalMinutesKey, 0);
        int coins = sharedPreferences.getInt(coinsKey, 0);

        binding.dailyStepCountTextView.setText(String.valueOf(dailyWorkouts));
        binding.stepCountTextView.setText(String.valueOf(totalWorkouts));
        binding.lastDayStepsCountTextView.setText(String.valueOf(totalMinutes));
        binding.coinsAmountTextview.setText(String.valueOf(coins));
    }
}
