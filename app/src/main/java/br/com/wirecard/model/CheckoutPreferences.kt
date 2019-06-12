package br.com.wirecard.model

data class CheckoutPreferences(
    val installments: List<Installment>,
    val redirectUrls: RedirectUrls
)