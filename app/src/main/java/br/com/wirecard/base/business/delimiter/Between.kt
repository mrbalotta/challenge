package br.com.wirecard.base.business.delimiter

import java.util.*

class Between(private val floor: String, private val ceil: String): RangeDelimiter() {
    constructor(fromDate: Date, untilDate: Date): this(formatter.format(fromDate), formatter.format(untilDate))
    constructor(fromValue: Number, untilValue: Number): this(fromValue.toString(), untilValue.toString())

    override val pattern get() = "bt(%s,%s)"

    override fun toString(): String {
        return String.format(pattern, floor, ceil)
    }

    override fun isValid(): Boolean {
        return floor.isNotEmpty() && ceil.isNotEmpty()
    }

    class Builder:
        DelimiterBuilder<Between> {
        protected var pairValue: Pair<String?,String?> = Pair(null, null)

        override fun build(): Between? {
            return if(pairValue.first != null && pairValue.second != null) {
                Between(pairValue.first as String, pairValue.second as String)
            } else null
        }

        fun add(first: Date, second: Date) {
            pairValue = Pair(formatter.format(first), formatter.format(second))
        }

        fun add(first: Number, second: Number) {
            pairValue = Pair(first.toString(), second.toString())
        }
    }
}