package com.enclave.barry.recipeapp.app.login.ui

import android.os.Bundle
import android.widget.Toast
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.app.login.vm.LoginViewModel
import com.enclave.barry.recipeapp.base.BaseBindingActivity
import com.enclave.barry.recipeapp.databinding.ActivityLoginBinding

class LoginActivity : BaseBindingActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutId = R.layout.activity_login
    override val viewModelClass = LoginViewModel::class

    override fun initView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        loginObserver()
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnLogin.setOnClickListener {
            viewModel.doLogin()
            hideKeyboard()
        }
        binding.btnCancel.setOnClickListener {
            hideKeyboard()
            finish()
        }
    }

    private fun loginObserver() {
        viewModel.loginSuccess.observe(this, {
            if (it) {
                Toast.makeText(this, getString(R.string.mess_login_success), Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.mess_login_failed), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}