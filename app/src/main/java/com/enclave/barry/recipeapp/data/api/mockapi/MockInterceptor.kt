package com.enclave.barry.recipeapp.data.api.mockapi

import android.content.Context
import com.enclave.barry.recipeapp.BuildConfig
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.data.api.model.LoginPost
import com.enclave.barry.recipeapp.util.FileUtil
import com.google.gson.Gson
import okhttp3.*
import okio.Buffer

class MockInterceptor(val context: Context, val gson: Gson) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val copy: Request = chain.request().newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)

            val jsonString = when {

                uri.contains("login") -> {
                    val loginPost = LoginPost("shin", "1234")
                    when (buffer.readUtf8()) {
                        gson.toJson(loginPost) -> FileUtil.getJsonFromAssets(
                            context,
                            R.raw.login_success
                        )
                        else -> FileUtil.getJsonFromAssets(context, R.raw.login_err)
                    }
                }
                else -> "None"
            }
            return chainReturn(chain, jsonString)
        } else {
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun chainReturn(chain: Interceptor.Chain, jsonString: String?): Response {
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(jsonString ?: "")
            .request(chain.request())
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    jsonString ?: ""
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}