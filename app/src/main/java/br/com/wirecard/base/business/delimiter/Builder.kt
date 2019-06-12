package br.com.wirecard.base.business.delimiter

import br.com.wirecard.base.business.dto.Either
import java.util.*

interface DelimiterBuilder<T: Delimiter> {
    fun build(): T?
}

abstract class NumberOrDateDelimiterBuilder:
    DelimiterBuilder<NumberOrDateDelimiter> {
    protected var value: Either<Number, Date>? = null

    fun add(value: Number) {
        this.value = Either.left(value)
    }

    fun add(value: Date) {
        this.value = Either.right(value)
    }
}