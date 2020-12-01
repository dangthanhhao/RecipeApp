package com.enclave.barry.recipeapp.app.login.vm

import androidx.lifecycle.MutableLiveData
import com.enclave.barry.recipeapp.base.BaseViewModel
import com.enclave.barry.recipeapp.data.api.model.ResponseWrapper
import com.enclave.barry.recipeapp.data.api.repository.LoginRepository
import com.enclave.barry.recipeapp.data.local.SharedPrefs
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepository,
    val sharedPrefs: SharedPrefs
) : BaseViewModel() {
    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginSuccess = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun doLogin() {
        val lUsername = userName.value.toString().trim()
        val lPassword = password.value.toString().trim()
        isLoading.postValue(true)
        if (lUsername.isNotEmpty() && lPassword.isNotEmpty()) {
            compositeDisposable.add(
                loginRepository.login(lUsername, lPassword).subscribe({
                    isLoading.postValue(false)
                    if (it.code == ResponseWrapper.SUCCESS_CODE) {
                        it.data?.let { user -> sharedPrefs.setLoginData(user).subscribe() }
                        loginSuccess.postValue(true)
                    } else {
                        loginSuccess.postValue(false)
                    }
                }, {
                    isLoading.postValue(false)
                    it.printStackTrace()
                    loginSuccess.postValue(false)
                })
            )
        } else {
            loginSuccess.postValue(false)
        }
    }

}