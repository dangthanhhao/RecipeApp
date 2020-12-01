package com.enclave.barry.recipeapp.data.api.repository

import com.enclave.barry.recipeapp.data.api.ApiService
import com.enclave.barry.recipeapp.data.api.model.LoginPost
import com.enclave.barry.recipeapp.data.api.model.ResponseWrapper
import com.enclave.barry.recipeapp.data.api.model.User
import com.enclave.barry.recipeapp.util.subscribeOnDataThread
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LoginRepository {
    override fun login(userName: String, password: String): Single<ResponseWrapper<User>> {
        return apiService.login(LoginPost(userName, password)).subscribeOnDataThread()
    }
}