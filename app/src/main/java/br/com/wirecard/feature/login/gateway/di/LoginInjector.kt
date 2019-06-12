package br.com.wirecard.feature.login.gateway.di

import br.com.wirecard.feature.login.business.interactor.LoginUseCase

interface LoginInjector {
    companion object {
        lateinit var injector: LoginInjector
    }

    val loginUseCase: LoginUseCase
}