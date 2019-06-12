package br.com.wirecard.feature.login.business.dto

data class Credential(
    val clientId: String = "",
    val clientSecret: String = "",
    val deviceId: String = "",
    val grantType: String = "",
    val password: String,
    val scope: String = "",
    val username: String
)