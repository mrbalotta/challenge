package br.com.wirecard.plugin.api

import com.google.gson.Gson
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPIBuilder(baseUrl: String, interceptors: List<Interceptor> = emptyList()):
AbstractBuilder<RetrofitAPI>(baseUrl, interceptors){

    override fun build(): RetrofitAPI {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitAPI::class.java)
    }
}