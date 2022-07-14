package com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ptithcm.thuan6420.basecleanarchitecture.R;
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource;
import com.ptithcm.thuan6420.basecleanarchitecture.data.models.User;
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentRegisterBinding;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.dialogs.IClickOnButtonDialogListener;
import com.ptithcm.thuan6420.basecleanarchitecture.ui.elements.dialogs.SuccessDialog;

public class RegisterFragment extends Fragment implements TextWatcher, View.OnClickListener, View.OnFocusChangeListener, IClickOnButtonDialogListener {

    private FragmentRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvToLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.layoutRegisterFragment.setOnClickListener(this);

        binding.etEmailRegister.addTextChangedListener(this);
        binding.etPasswordRegister.addTextChangedListener(this);
        binding.etFullNameRegister.addTextChangedListener(this);
        binding.etPhoneNumberRegister.addTextChangedListener(this);

        binding.etEmailRegister.setOnFocusChangeListener(this);
        binding.etPasswordRegister.setOnFocusChangeListener(this);
        binding.etFullNameRegister.setOnFocusChangeListener(this);
        binding.etPhoneNumberRegister.setOnFocusChangeListener(this);
    }

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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == binding.tvToLogin.getId()) {
            navigateToLoginFragment();
        } else if (id == binding.btnRegister.getId()) {
            register(binding.etEmailRegister.getText().toString().trim(),
                    binding.etPasswordRegister.getText().toString().trim(),
                    binding.etFullNameRegister.getText().toString().trim(),
                    binding.etPhoneNumberRegister.getText().toString().trim());
        } else if (id == binding.layoutRegisterFragment.getId()) {
            closeKeyBoard();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == binding.etEmailRegister.getId()) {
            binding.tvEmailRegisterError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (id == binding.etPasswordRegister.getId()) {
            binding.tvPasswordRegisterError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (id == binding.etFullNameRegister.getId()) {
            binding.tvFullNameRegisterError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (id == binding.etPhoneNumberRegister.getId()) {
            binding.tvPhoneNumberRegisterError.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void clickOnButtonDialog(int id) {
        binding.btnRegister.setEnabled(true);
        navigateToLoginFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void registerDataChanged(String email, String password, String fullName, String phoneNumber) {

        User user = new User(email, password, fullName, phoneNumber);

        if (user.getEmail().isEmpty()) {
            binding.tvEmailRegisterError.setText(R.string.error_empty_email);
        } else if (!user.isValidEmail()) {
            binding.tvEmailRegisterError.setText(R.string.error_invalid_email);
        } else {
            binding.tvEmailRegisterError.setText("");
        }

        if (user.getPassword().isEmpty()) {
            binding.tvPasswordRegisterError.setText(R.string.error_empty_password);
        } else if (!user.isValidPassword()) {
            binding.tvPasswordRegisterError.setText(R.string.error_invalid_password);
        } else {
            binding.tvPasswordRegisterError.setText("");
        }

        if (user.getFullName().isEmpty()) {
            binding.tvFullNameRegisterError.setText(R.string.error_empty_full_name);
        } else {
            binding.tvFullNameRegisterError.setText("");
        }

        if (user.getPhoneNumber().isEmpty()) {
            binding.tvPhoneNumberRegisterError.setText(R.string.error_empty_phone_number);
        } else if (!user.isValidPhoneNumber()) {
            binding.tvPhoneNumberRegisterError.setText(R.string.error_invalid_phone_number);
        } else {
            binding.tvPhoneNumberRegisterError.setText("");
        }
    }

    private void register(String email, String password, String fullName, String phoneNumber) {
        binding.btnRegister.setEnabled(false);
        closeKeyBoard();
        User user = new User(email, password, fullName, phoneNumber);
        if (!user.isValidUser()) {
            focusInvalidField(user);
            binding.btnRegister.setEnabled(true);
            return;
        }
        UserLocalDataSource.setUser(user);
        showSuccessAlertDialog();
    }

    private void showSuccessAlertDialog() {
        SuccessDialog successDialog = new SuccessDialog(getContext(),
                this, R.string.message_success_register);
        successDialog.show();
    }

    private void focusInvalidField(User user) {
        if (user.getEmail().isEmpty() || !user.isValidEmail()) {
            binding.etEmailRegister.requestFocus();
        } else if (user.getPassword().isEmpty() || !user.isValidPassword()) {
            binding.etPasswordRegister.requestFocus();
        } else if (user.getFullName().isEmpty()) {
            binding.etFullNameRegister.requestFocus();
        } else if (user.getPhoneNumber().isEmpty() || !user.isValidPhoneNumber()) {
            binding.etPhoneNumberRegister.requestFocus();
        }
    }

    private void navigateToLoginFragment() {
        NavHostFragment.findNavController(RegisterFragment.this)
                .navigate(R.id.action_registerFragment_to_loginFragment);
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