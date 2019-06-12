package br.com.wirecard.plugin.dao

interface IdentityDAO {
    fun getUniqueId(): String
    fun saveUniqueId(id: String)
}