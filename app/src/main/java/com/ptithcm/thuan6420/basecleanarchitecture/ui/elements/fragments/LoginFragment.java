package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.models.LoginModel;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        TextWatcher textWatcher =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loginDataChanged(binding.etEmailLogin.getText().toString().trim(),
                        binding.etPasswordLogin.getText().toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginDataChanged(binding.etEmailLogin.getText().toString().trim(),
                        binding.etPasswordLogin.getText().toString().trim());
            }
        };

        binding.etEmailLogin.addTextChangedListener(textWatcher);
        binding.etPasswordLogin.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void loginDataChanged(String email, String password) {

        LoginModel loginModel = new LoginModel(email, password);

        if (loginModel.getEmail().isEmpty()) {
            binding.tvEmailLoginError.setText("Email not null");
            binding.tvEmailLoginError.setVisibility(View.VISIBLE);
        }else if (!loginModel.isValidEmail()) {
            binding.tvEmailLoginError.setText("Email is invalid");
            binding.tvEmailLoginError.setVisibility(View.VISIBLE);
        }else {
            binding.tvEmailLoginError.setVisibility(View.GONE);
        }

        if (loginModel.getPassword().isEmpty()) {
            binding.tvPasswordLoginError.setText("Password not null");
            binding.tvPasswordLoginError.setVisibility(View.VISIBLE);
        }else if (!loginModel.isValidPassword()) {
            binding.tvPasswordLoginError.setText("Password must be > 8 characters");
            binding.tvPasswordLoginError.setVisibility(View.VISIBLE);
        }else {
            binding.tvPasswordLoginError.setVisibility(View.GONE);
        }
    }
}