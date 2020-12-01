package com.enclave.barry.recipeapp.data.api.repository

import com.enclave.barry.recipeapp.data.api.model.ResponseWrapper
import com.enclave.barry.recipeapp.data.api.model.User
import io.reactivex.Single

interface LoginRepository {
    fun login(userName: String, password: String): Single<ResponseWrapper<User>>
}