package br.com.wirecard.feature.login.gateway.mvvm

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.feature.login.business.dto.Credential
import br.com.wirecard.feature.login.business.interactor.LoginUseCase
import br.com.wirecard.feature.login.gateway.di.LoginInjector

class LoginViewModel(private val mockLoginUseCase: LoginUseCase? = null): BaseViewModel() {
    companion object {
        const val LOGIN_CHANNEL = "login"
    }

    private val loginUseCase by lazy { injectLoginUseCase() }

    override fun declareChannels() {
        availableChannels.add(LOGIN_CHANNEL)
    }

    fun login(username: String, password: String) {
        dispatch(LOGIN_CHANNEL, loginUseCase, Credential(username = username, password = password))
    }

    @VisibleForTesting(otherwise = PRIVATE)
    fun injectLoginUseCase(): LoginUseCase {
        return mockLoginUseCase ?: LoginInjector.injector.loginUseCase
    }
}