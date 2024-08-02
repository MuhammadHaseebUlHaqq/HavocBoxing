package com.example.ultimatefighterprogram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.ultimatefighterprogram.R;
import com.example.ultimatefighterprogram.activity.SignInActivity;
import com.example.ultimatefighterprogram.databinding.FragmentProfileBinding;
import com.example.ultimatefighterprogram.utilities.Constants;
import com.example.ultimatefighterprogram.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private PreferenceManager preferenceManager;

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferenceManager = new PreferenceManager(requireContext());
        loadUserDetails();
        setListeners();
    }

    private void setListeners() {
        binding.logoutbtn.setOnClickListener(v -> signOut());
    }

    private void loadUserDetails() {
        String email = preferenceManager.getString(Constants.KEY_EMAIL);
        String name = preferenceManager.getString(Constants.KEY_NAME);
        String image = preferenceManager.getString(Constants.KEY_IMAGE);

        Log.d("ProfileFragment", "Email: " + email); // Debug log for email
        Log.d("ProfileFragment", "Name: " + name);   // Debug log for name
        Log.d("ProfileFragment", "Image: " + image); // Debug log for image

        binding.userName.setText(name);
        binding.emailText.setText(email);

        if (image != null) {
            byte[] bytes = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.profileImage.setImageBitmap(bitmap);
        }
        if(name.equals("Raja Ahsan (Trainer)")){
            binding.userStatus.setText(R.string.trainer);
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        showToast("Signing out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    // Clear preferences
                    preferenceManager.clear();
                    // Start sign-in activity
                    Intent intent = new Intent(requireContext(), SignInActivity.class);
                    // Clear back stack and start the activity as a new task
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }
}
