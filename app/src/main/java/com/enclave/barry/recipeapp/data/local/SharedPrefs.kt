package com.enclave.barry.recipeapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.enclave.barry.recipeapp.data.api.model.User
import com.enclave.barry.recipeapp.util.subscribeOnDataThread
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(val context: Context) {
    companion object {
        const val PREFS_NAME = "recipe-app-shared-prefs"
        const val IS_FIRST_TIME_RUN = "is-first-time-run"
        const val IS_LOGIN = "is-login"
        const val CURRENT_TOKEN = "current-token"
    }

    var mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setIsfirstTimeRun(boolean: Boolean): Completable {
        return Completable.fromAction {
            val editor = mSharedPreferences.edit()
            editor.putBoolean(IS_FIRST_TIME_RUN, boolean)
            editor.apply()
        }.subscribeOnDataThread()
    }

    fun getIsFirstTimeRun(): Single<Boolean> {
        val singleObj = Single.create<Boolean> {
            it.onSuccess(mSharedPreferences.getBoolean(IS_FIRST_TIME_RUN, true))
        }
        return singleObj.subscribeOnDataThread()
    }

    fun setLoginData(user: User): Completable {
        return Completable.fromAction {
            val editor = mSharedPreferences.edit()
            editor.putBoolean(IS_LOGIN, true)
            editor.putString(CURRENT_TOKEN, user.accessToken)
            editor.apply()
        }.subscribeOnDataThread()
    }

    fun getLoginStatus(): Single<Boolean> {
        val singleObj = Single.create<Boolean> {
            it.onSuccess(mSharedPreferences.getBoolean(IS_LOGIN, false))
        }
        return singleObj.subscribeOnDataThread()
    }
}