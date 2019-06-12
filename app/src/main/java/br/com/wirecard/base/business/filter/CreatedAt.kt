package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.business.dto.Either
import java.util.*

class CreatedAt: Filter<Delimiter>("createdAt") {

    companion object {
        fun greaterThan(date: Date): CreatedAt {
            return CreatedAt().apply { set(GreaterThan(Either.right(date)))}
        }

        fun lessThan(date: Date): CreatedAt {
            return CreatedAt().apply { set(LessThan(Either.right(date))) }
        }

        fun greaterOrEqualsTo(date: Date): CreatedAt {
            return CreatedAt().apply { set(GreaterOrEqualsTo(Either.right(date))) }
        }

        fun lessOrEqualsTo(date: Date): CreatedAt {
            return CreatedAt().apply { set(LessOrEqualsTo(Either.right(date))) }
        }

        fun between(fromDate: Date, untilDate: Date): CreatedAt {
            return CreatedAt().apply { set(Between(fromDate, untilDate)) }
        }

        fun inRange(range: Array<Date>): CreatedAt {
            return CreatedAt().apply { set(InRange(range)) }
        }
    }
}