package br.com.wirecard.feature.splash.gateway.di

import br.com.wirecard.feature.splash.business.GetTokenUseCase

interface SplashInjector {
    companion object {
        lateinit var injector: SplashInjector
    }

    val tokenUseCase: GetTokenUseCase
}