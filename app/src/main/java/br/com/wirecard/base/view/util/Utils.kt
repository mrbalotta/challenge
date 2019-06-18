package br.com.wirecard.base.view.util

import java.math.BigDecimal
import java.math.BigInteger

fun currency(currency: String, value: Double): String {
    return "$currency ${String.format("%.2f", value).replace(".", ",")}"
}

fun currency(currency: String, value: Int): String {
    return currency(currency, (value/100).toDouble())
}

fun centsParser(value: String): BigDecimal {
    return BigDecimal(value)
        .setScale(2, BigDecimal.ROUND_FLOOR)
        .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
}

fun centsParser(value: BigDecimal): BigInteger {
    return value
        .setScale(2, BigDecimal.ROUND_FLOOR)
        .multiply(BigDecimal(100))
        .toBigInteger()
}