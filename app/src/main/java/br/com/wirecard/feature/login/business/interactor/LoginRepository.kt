package br.com.wirecard.feature.login.business.interactor

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.feature.login.business.dto.Credential

interface LoginRepository {
    fun getToken(credential: Credential): Token
    fun saveToken(token: Token)
}