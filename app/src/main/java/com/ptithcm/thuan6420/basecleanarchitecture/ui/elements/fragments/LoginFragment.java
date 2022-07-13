package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.fragments;

import static com.ptithcm.thuan6420.basecleanarchitecture.R.string;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.models.User;
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.UserRepository;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutErrorDialogBinding;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.LayoutSuccessDialogBinding;

public class LoginFragment extends Fragment implements TextWatcher, View.OnClickListener, View.OnFocusChangeListener {

    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToRegister.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.layoutLoginFragment.setOnClickListener(this);

        binding.etEmailLogin.addTextChangedListener(this);
        binding.etPasswordLogin.addTextChangedListener(this);

        binding.etEmailLogin.setOnFocusChangeListener(this);
        binding.etPasswordLogin.setOnFocusChangeListener(this);
    }

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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.tvToRegister.getId()) {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        } else if (id == binding.btnLogin.getId()) {
            login(binding.etEmailLogin.getText().toString().trim(),
                    binding.etPasswordLogin.getText().toString().trim());
        } else if (id == binding.layoutLoginFragment.getId()) {
            closeKeyBoard();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == binding.etEmailLogin.getId()) {
            binding.tvEmailLoginError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (id == binding.etPasswordLogin.getId()) {
            binding.tvPasswordLoginError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void loginDataChanged(String email, String password) {
        User user = new User(email, password);

        if (user.getEmail().isEmpty()) {
            binding.tvEmailLoginError.setText(string.error_empty_email);
        } else if (!user.isValidEmail()) {
            binding.tvEmailLoginError.setText(string.error_invalid_email);
        } else {
            binding.tvEmailLoginError.setText("");
        }

        if (user.getPassword().isEmpty()) {
            binding.tvPasswordLoginError.setText(string.error_empty_password);
        } else if (!user.isValidPassword()) {
            binding.tvPasswordLoginError.setText(string.error_invalid_password);
        } else {
            binding.tvPasswordLoginError.setText("");
        }
    }

    private void login(String email, String password) {
        closeKeyBoard();
        User user = new User(email, password);
        if (!user.isValidLoginUser()) {
            focusInvalidField(user);
            return;
        }
        if (!UserRepository.isLoginSuccess(user)) {
            showErrorAlertDialog();
            return;
        }
        showSuccessAlertDialog();
    }

    private void showErrorAlertDialog() {
        Log.e("ErrorDialog", "Đã khởi tạo dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        LayoutErrorDialogBinding dialogBinding = LayoutErrorDialogBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        dialogBinding.tvErrorMessage.setText(R.string.message_error_login);
        final AlertDialog alertDialog = builder.create();
        dialogBinding.btnError.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }

    private void focusInvalidField(User user) {
        if (user.getEmail().isEmpty() || !user.isValidEmail()) {
            binding.etEmailLogin.requestFocus();
        } else if (user.getPassword().isEmpty() || !user.isValidPassword()) {
            binding.etPasswordLogin.requestFocus();
        }
    }

    private void showSuccessAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        LayoutSuccessDialogBinding dialogBinding = LayoutSuccessDialogBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        dialogBinding.tvSuccessMessage.setText(R.string.message_success_login);
        final AlertDialog alertDialog = builder.create();
        dialogBinding.btnSuccess.setOnClickListener(v -> {
            alertDialog.dismiss();
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_mainActivity);
        });
        alertDialog.show();
    }

    private void closeKeyBoard() {
        View view = this.requireActivity().getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}