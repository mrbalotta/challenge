package br.com.wirecard.plugin.dao

import br.com.wirecard.base.business.dto.Token

interface SessionDAO {
    fun getToken(): Token
    fun saveToken(token: Token)
}