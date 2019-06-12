package br.com.wirecard.base.business.dto

data class Token(
    val accessToken: String,
    val id: String) {
    fun isExpired(): Boolean {
        return false
    }
}