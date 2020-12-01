package com.enclave.barry.recipeapp.data.api.model

data class ResponseWrapper<T>(
    var code: Int?,
    var message: String?,
    var data: T?
) {
    companion object {
        const val SUCCESS_CODE = 200
    }
}