package br.com.wirecard.model

data class Checkout(
    val payBoleto: PayRedirect,
    val payCheckout: PayRedirect,
    val payCreditCard: PayRedirect,
    val payOnlineBankDebitItau: PayRedirect
)