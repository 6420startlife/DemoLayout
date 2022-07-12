package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setControl();
        setEvent();
    }

    private void setControl() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    }

    private void setEvent() {
    }
}