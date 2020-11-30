package com.enclave.barry.recipeapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.enclave.barry.recipeapp.util.subscribeOnDataThread
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefs @Inject constructor(val context: Context) {
    companion object {
        val PREFS_NAME = "recipe-app-shared-prefs"
        val IS_FIRST_TIME_RUN = "is-first-time-run"
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

}