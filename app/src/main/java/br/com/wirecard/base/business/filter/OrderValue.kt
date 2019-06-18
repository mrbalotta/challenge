package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.business.dto.Either

class OrderValue: Filter<Delimiter>("wrappedValue") {
    companion object {
        fun greaterThan(value: Number): OrderValue {
            return OrderValue().apply { set(GreaterThan(Either.left(value))) }
        }

        fun lessThan(value: Number): OrderValue {
            return OrderValue().apply { set(LessThan(Either.left(value))) }
        }

        fun greaterOrEqualsTo(value: Number): OrderValue {
            return OrderValue().apply { set(GreaterOrEqualsTo(Either.left(value))) }
        }

        fun lessOrEqualsTo(value: Number): OrderValue {
            return OrderValue().apply { set(LessOrEqualsTo(Either.left(value))) }
        }

        fun between(fromValue: Number, untilValue: Number): OrderValue {
            return OrderValue().apply { set(Between(fromValue, untilValue)) }
        }

        fun inRange(range: Array<Number>): OrderValue {
            return OrderValue().apply { set(InRange(range)) }
        }
    }
}