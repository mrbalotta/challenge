package br.com.wirecard.model

data class Address(
    val city: String,
    val complement: String,
    val country: String,
    val district: String,
    val state: String,
    val street: String,
    val streetNumber: String,
    val type: String,
    val zipCode: String
)