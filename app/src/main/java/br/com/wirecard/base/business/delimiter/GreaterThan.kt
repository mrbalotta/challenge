package br.com.wirecard.base.business.delimiter

import br.com.wirecard.base.business.dto.Either
import java.util.*

class GreaterThan(value: Either<Number, Date>): NumberOrDateDelimiter(value) {
    override val pattern get() = "gt(%s)"

    class Builder: NumberOrDateDelimiterBuilder() {
        override fun build(): NumberOrDateDelimiter? {
            return if(value != null) GreaterThan(value!!) else null
        }
    }
}