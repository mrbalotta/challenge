package br.com.wirecard.base.business.delimiter

import br.com.wirecard.base.business.dto.Either
import java.util.*

class LessOrEqualsTo(value: Either<Number, Date>): NumberOrDateDelimiter(value) {
    override val pattern get() = "le(%s)"

    class Builder: NumberOrDateDelimiterBuilder() {
        override fun build(): NumberOrDateDelimiter? {
            return if(value != null) LessOrEqualsTo(value!!) else null
        }
    }
}