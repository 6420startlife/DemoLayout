package com.ptithcm.thuan6420.basecleanarchitecture.ui.login.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.KEY_EMAIL_FORGOT
import com.ptithcm.thuan6420.basecleanarchitecture.R
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentLoginBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.BaseFragment
import com.ptithcm.thuan6420.basecleanarchitecture.util.isValidEmail
import com.ptithcm.thuan6420.basecleanarchitecture.util.isValidPassword
import com.ptithcm.thuan6420.basecleanarchitecture.ui.home.HomeActivity
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by viewModels()
    @Inject lateinit var callbackManager: CallbackManager
    @Inject lateinit var gsc: GoogleSignInClient
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            task.getResult(ApiException::class.java)
            navigateToMain()
        } catch (exception: Exception) {
            showErrorDialog("Something went wrong!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    navigateToMain()
                    Log.e("T64", "Đã thành công")
                }

                override fun onCancel() {
                    Log.e("T64", "Đã cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.e("T64", "Đã error")
                }
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener(this)
        binding.tvToRegister.setOnClickListener(this)
        binding.layoutLoginFragment.setOnClickListener(this)
        binding.tvForgot.setOnClickListener(this)
        binding.ivFacebookLogin.setOnClickListener(this)
        binding.ivGoogleLogin.setOnClickListener(this)
    }

    private fun login() {
        if (binding.etEmailLoginLayout.isValidEmail().not() ||
            binding.etPasswordLoginLayout.isValidPassword().not()) {
            return
        }
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        viewModel.login(email, password).observe(this) {
            it?.let {
                submit(it.status, it.message, it.data)
            }
        }
    }

    private fun loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this,
            callbackManager, listOf())
    }

    private fun loginGoogle() {
        val signInIntent = gsc.signInIntent
        launcher.launch(signInIntent)
    }

    override fun onClickOnSuccessDialog() {
        super.onClickOnSuccessDialog()
        navigateToMain()
    }

    private fun navigateToMain() {
        this.requireActivity().finishAffinity()
        this.requireActivity().startActivity(Intent(this.requireActivity(), HomeActivity::class.java))
    }

    private fun navigateToRegister() {
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToForgotPassword() {
        val bundle = Bundle()
        bundle.putString(KEY_EMAIL_FORGOT, binding.etEmailLogin.text.toString().trim())
        NavHostFragment.findNavController(this@LoginFragment)
            .navigate(R.id.action_loginFragment_to_forgotPasswordFragment, bundle)
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when (v) {
            binding.btnLogin -> login()
            binding.layoutLoginFragment -> closeKeyBoard()
            binding.tvToRegister -> navigateToRegister()
            binding.tvForgot -> navigateToForgotPassword()
            binding.ivFacebookLogin -> loginFacebook()
            binding.ivGoogleLogin -> loginGoogle()
        }
    }
}