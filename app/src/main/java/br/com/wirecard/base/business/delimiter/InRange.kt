package br.com.wirecard.base.business.delimiter

import java.util.*

class InRange(private val range: List<String>): RangeDelimiter() {
    constructor(dateRange: Array<Date>): this(dateRange.map { formatter.format(it) })
    constructor(valueRange: Array<Number>): this(valueRange.map { it.toString() })

    override val pattern get() = "in(%s)"

    override fun toString(): String {
        return String.format(pattern, range.joinToString(separator = ","))
    }

    override fun isValid(): Boolean {
        return range.isNotEmpty()
    }

    class Builder: DelimiterBuilder<InRange> {
        private val values = mutableListOf<String>()

        override fun build(): InRange {
            return InRange(values)
        }

        fun add(date: Date) {
            values.add(Delimiter.formatter.format(date))
        }

        fun add(value: Number) {
            values.add(value.toString())
        }
    }
}