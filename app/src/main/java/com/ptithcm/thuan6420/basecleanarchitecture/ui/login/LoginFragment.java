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
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding binding;
    private UserController userController;

    private void initController() {
        userController = new UserController(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        initController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToRegister.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.layoutLoginFragment.setOnClickListener(this);
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
        userController.setUserEmail(binding.etEmailLogin.getText().toString().trim());
        userController.setUserPassword(binding.etPasswordLogin.getText().toString().trim());
    }

    public void showErrorEmail(String errorMessage) {
        binding.etEmailLoginLayout.setError(errorMessage);
    }

    public void showErrorPassword(String errorMessage) {
        binding.etPasswordLoginLayout.setError(errorMessage);
    }

    public void navigateToMainActivity() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_mainActivity);
    }

    public void navigateToRegisterFragment() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_registerFragment);
    }

    public void turnOnLoading() {
        binding.progressBarLogin.setVisibility(View.VISIBLE);
    }

    public void turnOffLoading() {
        binding.progressBarLogin.setVisibility(View.GONE);
    }

    public void setEnabledButton(boolean enabled) {
        binding.btnLogin.setEnabled(enabled);
    }
}