package br.com.wirecard.model

data class Subtotals(
    val addition: Int,
    val discount: Int,
    val items: Int,
    val shipping: Int
)