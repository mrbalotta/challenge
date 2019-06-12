package br.com.wirecard.plugin.dao

import android.content.Context
import android.content.SharedPreferences
import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.exception.AuthenticationException
import com.google.gson.Gson

class SharedPreferencesDAO(context: Context): SessionDAO, IdentityDAO {
    private val sharedPreferences: SharedPreferences
    private val name = "Wirecard"
    private val tokenPrefName = "token"
    private val identityPrefName = "identity"

    init {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    override fun getToken(): Token {
        val tokenJson = sharedPreferences.getString(tokenPrefName, "")
        if(tokenJson == null || tokenJson.isEmpty()) throw AuthenticationException()
        return Gson().fromJson(tokenJson, Token::class.java)
    }

    override fun saveToken(token: Token) {
        val tokenJson = Gson().toJson(token)
        sharedPreferences.edit().putString(tokenPrefName, tokenJson).apply()
    }

    override fun getUniqueId(): String {
        return sharedPreferences.getString(identityPrefName, "")!!
    }

    override fun saveUniqueId(id: String) {
        sharedPreferences.edit().putString(identityPrefName, id).apply()
    }
}
