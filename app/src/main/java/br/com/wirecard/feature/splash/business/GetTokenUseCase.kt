package br.com.wirecard.feature.splash.business

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.UseCase
import br.com.wirecard.base.business.interactor.ValueOutput

class GetTokenUseCase(private val repository: SplashRepository): UseCase<Nothing, Token>() {

    override fun execute(param: Nothing?): Output<Token> {
        val token = repository.getSavedToken()
        return ValueOutput(token)
    }
}