package br.com.wirecard.model

data class Amount(
    val currency: String,
    val fees: Int,
    val liquid: Int,
    val otherReceivers: Int,
    val paid: Int,
    val refunds: Int,
    val subtotals: Subtotals,
    val total: Int
)