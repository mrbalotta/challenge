package br.com.wirecard.plugin.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.annotation.VisibleForTesting.PROTECTED
import br.com.wirecard.base.business.exception.AuthenticationException
import br.com.wirecard.base.business.exception.HttpException
import br.com.wirecard.base.business.exception.InternetConnectionException
import br.com.wirecard.plugin.api.RetrofitAPI
import okhttp3.Interceptor
import retrofit2.Call
import java.io.IOException

abstract class BaseRepository(protected val baseUrl: String) {

    protected fun <R> getBodyOrThrow(call: Call<R>): R {
        try {
            val response = call.execute()
            if(response.isSuccessful) return response.body()!!
            if(response.code() == 401) {
                Log.w("ALE", "code is 401")
                throw AuthenticationException()
            }
            throw HttpException(response.code(), response.message())
        } catch(e: IOException) {
            e.printStackTrace()
            throw InternetConnectionException()
        }
    }

    @VisibleForTesting(otherwise = PROTECTED)
    internal abstract fun getService(interceptors: List<Interceptor> = emptyList()): RetrofitAPI

    @VisibleForTesting(otherwise = PRIVATE)
    internal fun dumbRequest() {
        getBodyOrThrow(getService().dumbRequest())
    }
}