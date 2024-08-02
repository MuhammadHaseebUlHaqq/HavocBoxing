package com.example.ultimatefighterprogram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.activity.CardioConditioningActivity;
import com.example.ultimatefighterprogram.activity.ShadowBoxing1;
import com.example.ultimatefighterprogram.activity.ShadowBoxing7;
import com.example.ultimatefighterprogram.activity.StrengthTrainingActivity;
import com.example.ultimatefighterprogram.activity.WorkoutActivity;
import com.example.ultimatefighterprogram.databinding.FragmentStatsBinding;

import java.util.Arrays;

public class StatisticsFragment extends Fragment {
    private FragmentStatsBinding binding;

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views and logic here
        setListeners();
    }

    private void setListeners() {
        binding.shadowboxing.setOnClickListener(v ->
                startActivity(new Intent(getContext(), ShadowBoxing1.class))
        );
        binding.advancedboxing.setOnClickListener(v ->
                startActivity(new Intent(getContext(), WorkoutActivity.class))
        );
        binding.strength.setOnClickListener(v ->
                startActivity(new Intent(getContext(), StrengthTrainingActivity.class))
        );
        binding.cardio.setOnClickListener(v ->
                startActivity(new Intent(getContext(), CardioConditioningActivity.class))
        );

        binding.bigworkout1.setOnClickListener(v ->
                startWorkoutSequence(StrengthTrainingActivity.class, ShadowBoxing1.class, CardioConditioningActivity.class)
        );

        binding.bigworkout2.setOnClickListener(v ->
                startWorkoutSequence(ShadowBoxing1.class, WorkoutActivity.class, CardioConditioningActivity.class)
        );

        binding.bigworkout3.setOnClickListener(v ->
                startWorkoutSequence(CardioConditioningActivity.class, StrengthTrainingActivity.class, WorkoutActivity.class)
        );
    }

    private void startWorkoutSequence(Class<?>... activities) {
        Intent intent = new Intent(getContext(), activities[0]);
        intent.putExtra("nextActivities", Arrays.copyOfRange(activities, 1, activities.length));
        startActivity(intent);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear the binding object when the view is destroyed
    }
}
