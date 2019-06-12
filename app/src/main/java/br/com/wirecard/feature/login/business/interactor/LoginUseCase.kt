package br.com.wirecard.feature.login.business.interactor

import android.util.Log
import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.UseCase
import br.com.wirecard.base.business.interactor.ValueOutput
import br.com.wirecard.base.business.strategy.DeviceIDStrategy
import br.com.wirecard.feature.login.business.dto.Credential

class LoginUseCase(private val repository: LoginRepository, private val deviceIDStrategy: DeviceIDStrategy): UseCase<Credential, Token>() {

    override fun guard(param: Credential?): Boolean {
        return param != null
    }

    override fun execute(param: Credential?): Output<Token> {
        val token = repository.getToken(credential(param))
        repository.saveToken(token)
        return ValueOutput(token)
    }

    private fun credential(param: Credential?): Credential {
        return param!!.copy(
            clientId = "APP-H1DR0RPHV7SP",
            clientSecret = "05acb6e128bc48b2999582cd9a2b9787",
            grantType = "password",
            deviceId = deviceIDStrategy.deviceId,
            scope = "APP_ADMIN"
        )
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        error.printStackTrace()
    }
}