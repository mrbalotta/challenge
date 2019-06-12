package br.com.wirecard.model

data class Receiver(
    val amount: Amount,
    val feePayor: Boolean,
    val moipAccount: MoipAccount,
    val type: String
)