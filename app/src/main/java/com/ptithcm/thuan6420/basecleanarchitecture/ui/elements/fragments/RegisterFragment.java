package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.models.RegisterModel;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        TextWatcher textWatcher =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                registerDataChanged(binding.etEmailRegister.getText().toString().trim(),
                        binding.etPasswordRegister.getText().toString().trim(),
                        binding.etFullNameRegister.getText().toString().trim(),
                        binding.etPhoneNumberRegister.getText().toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerDataChanged(binding.etEmailRegister.getText().toString().trim(),
                        binding.etPasswordRegister.getText().toString().trim(),
                        binding.etFullNameRegister.getText().toString().trim(),
                        binding.etPhoneNumberRegister.getText().toString().trim());
            }
        };

        binding.etEmailRegister.addTextChangedListener(textWatcher);
        binding.etPasswordRegister.addTextChangedListener(textWatcher);
        binding.etFullNameRegister.addTextChangedListener(textWatcher);
        binding.etPhoneNumberRegister.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void registerDataChanged(String email, String password, String fullName, String phoneNumber) {

        RegisterModel registerModel = new RegisterModel(email, password, fullName, phoneNumber);

        if (registerModel.getEmail().isEmpty()) {
            binding.tvEmailRegisterError.setText("Email not null");
            binding.tvEmailRegisterError.setVisibility(View.VISIBLE);
        }else if (!registerModel.isValidEmail()) {
            binding.tvEmailRegisterError.setText("Email is invalid");
            binding.tvEmailRegisterError.setVisibility(View.VISIBLE);
        }else {
            binding.tvEmailRegisterError.setVisibility(View.GONE);
        }

        if (registerModel.getPassword().isEmpty()) {
            binding.tvPasswordRegisterError.setText("Password not null");
            binding.tvPasswordRegisterError.setVisibility(View.VISIBLE);
        }else if (!registerModel.isValidPassword()) {
            binding.tvPasswordRegisterError.setText("Password must be > 8 characters");
            binding.tvPasswordRegisterError.setVisibility(View.VISIBLE);
        }else {
            binding.tvPasswordRegisterError.setVisibility(View.GONE);
        }

        if (registerModel.getFullName().isEmpty()) {
            binding.tvFullNameRegisterError.setText("Full name not null");
            binding.tvFullNameRegisterError.setVisibility(View.VISIBLE);
        }else {
            binding.tvFullNameRegisterError.setVisibility(View.GONE);
        }

        if (registerModel.getPhoneNumber().isEmpty()) {
            binding.tvPhoneNumberRegisterError.setText("Phone number not null");
            binding.tvPhoneNumberRegisterError.setVisibility(View.VISIBLE);
        }else if (!registerModel.isValidPhoneNumber()) {
            binding.tvPhoneNumberRegisterError.setText("Phone number must be 10 numbers and start by 0");
            binding.tvPhoneNumberRegisterError.setVisibility(View.VISIBLE);
        }else {
            binding.tvPhoneNumberRegisterError.setVisibility(View.GONE);
        }
    }
}