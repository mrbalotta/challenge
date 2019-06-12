package br.com.wirecard.model

data class ShippingAddress(
    val city: String,
    val complement: String,
    val country: String,
    val district: String,
    val state: String,
    val street: String,
    val streetNumber: String,
    val zipCode: String
)