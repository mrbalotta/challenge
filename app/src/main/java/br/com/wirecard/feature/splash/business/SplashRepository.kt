package br.com.wirecard.feature.splash.business

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.interactor.Output

interface SplashRepository {
    fun getSavedToken(): Token
}