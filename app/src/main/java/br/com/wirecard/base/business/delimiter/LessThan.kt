package br.com.wirecard.base.business.delimiter

import br.com.wirecard.base.business.dto.Either
import java.util.*

class LessThan(value: Either<Number, Date>): NumberOrDateDelimiter(value) {
    override val pattern get() = "lt(%s)"

    class Builder: NumberOrDateDelimiterBuilder() {
        override fun build(): NumberOrDateDelimiter? {
            return if(value != null) LessThan(value!!) else null
        }
    }
}