package br.com.wirecard.plugin.repository

import br.com.wirecard.plugin.api.RetrofitAPI
import br.com.wirecard.plugin.api.RetrofitAPIBuilder
import br.com.wirecard.plugin.dao.SessionDAO
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

abstract class LoggedInRepository(baseUrl: String, private val dao: SessionDAO): BaseRepository(baseUrl) {

    protected open fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("Authorization", "OAuth ${dao.getToken().accessToken}")

            chain.proceed(request.build())
        }
    }

    override fun getService(interceptors: List<Interceptor>): RetrofitAPI {
        val authInterceptor = getAuthInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val list = mutableListOf(authInterceptor, loggingInterceptor).apply { addAll(interceptors) }
        return RetrofitAPIBuilder(baseUrl, list).build()
    }
}