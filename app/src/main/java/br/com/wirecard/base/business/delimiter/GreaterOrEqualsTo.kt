package br.com.wirecard.base.business.delimiter

import br.com.wirecard.base.business.dto.Either
import java.util.*

class GreaterOrEqualsTo(value: Either<Number, Date>): NumberOrDateDelimiter(value) {
    override val pattern get() = "ge(%s)"

    class Builder: NumberOrDateDelimiterBuilder() {
        override fun build(): NumberOrDateDelimiter? {
            return if(value != null) GreaterOrEqualsTo(value!!) else null
        }
    }
}