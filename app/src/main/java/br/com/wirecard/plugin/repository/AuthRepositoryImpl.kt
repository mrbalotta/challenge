package br.com.wirecard.plugin.repository

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.feature.login.business.dto.Credential
import br.com.wirecard.plugin.api.RetrofitAPI
import br.com.wirecard.plugin.api.RetrofitAPIBuilder
import br.com.wirecard.plugin.dao.SessionDAO
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class AuthRepositoryImpl(baseUrl: String, private val dao: SessionDAO): BaseRepository(baseUrl), AuthRepository {
    override fun getSavedToken(): Token {
        return dao.getToken()
    }

    override fun getToken(credential: Credential): Token {
        val call = getService().getToken(
                credential.clientId,
                credential.clientSecret,
                credential.grantType,
                credential.username,
                credential.password,
                credential.deviceId,
                credential.scope)
        return getBodyOrThrow(call)
    }

    override fun getService(interceptors: List<Interceptor>): RetrofitAPI {
        val list = mutableListOf<Interceptor>(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC }
        ).apply { addAll(interceptors) }

        return RetrofitAPIBuilder(baseUrl, list).build()
    }

    override fun saveToken(token: Token) {
        dao.saveToken(token)
    }
}