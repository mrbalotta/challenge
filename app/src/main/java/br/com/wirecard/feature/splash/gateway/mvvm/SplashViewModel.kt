package br.com.wirecard.feature.splash.gateway.mvvm

import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.feature.splash.business.GetTokenUseCase
import br.com.wirecard.feature.splash.gateway.di.SplashInjector

class SplashViewModel(private val mockTokenUseCase: GetTokenUseCase? = null): BaseViewModel() {
    companion object {
        const val SPLASH_CHANNEL = "splash"
    }

    private val tokenUseCase by lazy { injectTokenUseCase() }

    fun isUserLoggedIn() {
        dispatch(SPLASH_CHANNEL, tokenUseCase)
    }

    private fun injectTokenUseCase(): GetTokenUseCase {
        return mockTokenUseCase ?: SplashInjector.injector.tokenUseCase
    }

    override fun declareChannels() {
        availableChannels.add(SPLASH_CHANNEL)
    }
}