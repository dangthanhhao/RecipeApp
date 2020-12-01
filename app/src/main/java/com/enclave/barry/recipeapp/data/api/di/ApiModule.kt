package com.enclave.barry.recipeapp.data.api.di

import android.content.Context
import com.enclave.barry.recipeapp.BuildConfig
import com.enclave.barry.recipeapp.data.api.ApiService
import com.enclave.barry.recipeapp.data.api.mockapi.MockInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class ApiModule {

    companion object {
        private const val REQUEST_TIMEOUT_MILLIS = 15000L
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @Provides
    @Named("api")
    fun provideHttpClient(context: Context, gson: Gson): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            readTimeout(REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            writeTimeout(REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(interceptor)
                addInterceptor(MockInterceptor(context, gson))
            }
        }.build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    @Provides
    @Named("api")
    fun provideRetrofit(gson: Gson, @Named("api") client: OkHttpClient) = Retrofit.Builder().apply {
        addConverterFactory(GsonConverterFactory.create(gson))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        baseUrl(BASE_URL)
        client(client)
    }.build()

    @Provides
    fun provideApiEndpoint(@Named("api") retrofit: Retrofit) =
        retrofit.create(ApiService::class.java)


}