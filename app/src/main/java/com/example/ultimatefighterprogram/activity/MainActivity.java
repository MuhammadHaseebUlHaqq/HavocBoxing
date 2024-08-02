package com.example.ultimatefighterprogram.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.databinding.ActivityMainBinding;
import com.example.ultimatefighterprogram.fragments.HomeFragment;
import com.example.ultimatefighterprogram.fragments.MessagingFragment;
import com.example.ultimatefighterprogram.fragments.ProfileFragment;
import com.example.ultimatefighterprogram.fragments.StatisticsFragment;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private BottomNavigationView bottomNav;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        chipNavigationBar = findViewById(R.id.bottomNav);
        if (chipNavigationBar == null) {
            Log.e("MainActivity", "chipNavigationBar is null");
        } else {
            Log.d("MainActivity", "chipNavigationBar initialized successfully");
        }
        bottomMenu();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new HomeFragment())
                    .commit();
        }

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            if (i == R.id.bottom_nav_dashboard) {
                fragment = new HomeFragment();
            } else if (i == R.id.bottom_nav_Statistics) {
                fragment = new StatisticsFragment();
                chipNavigationBar.dismissBadge(R.id.bottom_nav_Statistics);
            }
            else if (i == R.id.bottom_nav_wallet) {
                fragment=new MessagingFragment();
            }
            else if (i == R.id.bottom_nav_account) {
                fragment = new ProfileFragment();
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.fragment_container_view, fragment)
                        .commit();
            }
        });
    }}



