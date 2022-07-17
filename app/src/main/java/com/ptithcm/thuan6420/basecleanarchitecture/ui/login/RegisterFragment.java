package com.ptithcm.thuan6420.basecleanarchitecture.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FragmentRegisterBinding binding;
    private UserController userController;

    private void initController() {
        userController = new UserController(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        initController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.layoutRegisterFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userController.handleOnClick(v);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    public void getValue() {
        userController.setUserEmail(binding.etEmailRegister.getText().toString().trim());
        userController.setUserPassword(binding.etPasswordRegister.getText().toString().trim());
        userController.setUserFullName(binding.etFullNameRegister.getText().toString().trim());
        userController.setUserPhoneNumber(binding.etPhoneNumberRegister.getText().toString().trim());
    }

    public void showErrorEmail(String errorMessage) {
        binding.etEmailRegisterLayout.setError(errorMessage);
    }

    public void showErrorPassword(String errorMessage) {
        binding.etPasswordRegisterLayout.setError(errorMessage);
    }

    public void showErrorFullName(String errorMessage) {
        binding.etFullNameRegisterLayout.setError(errorMessage);
    }

    public void showErrorPhoneNumber(String errorMessage) {
        binding.etPhoneNumberRegisterLayout.setError(errorMessage);
    }

    public void navigateToLoginFragment() {
        NavHostFragment.findNavController(RegisterFragment.this)
                .navigate(R.id.action_registerFragment_to_loginFragment);
    }

    public void turnOnLoading() {
        binding.progressBarRegister.setVisibility(View.VISIBLE);
    }

    public void turnOffLoading() {
        binding.progressBarRegister.setVisibility(View.GONE);
    }

    public void setEnabledButton(boolean enabled) {
        binding.btnRegister.setEnabled(enabled);
    }
}