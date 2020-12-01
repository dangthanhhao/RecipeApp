package com.enclave.barry.recipeapp.data.api

import com.enclave.barry.recipeapp.data.api.model.LoginPost
import com.enclave.barry.recipeapp.data.api.model.ResponseWrapper
import com.enclave.barry.recipeapp.data.api.model.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body loginPost: LoginPost): Single<ResponseWrapper<User>>
}