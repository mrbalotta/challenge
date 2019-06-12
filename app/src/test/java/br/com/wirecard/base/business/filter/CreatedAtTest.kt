package br.com.wirecard.base.business.filter

import org.junit.Test

import org.junit.Before
import java.util.*
import br.com.wirecard.base.business.delimiter.Delimiter.Companion.formatter as formatter

class CreatedAtTest: AbstractFilterTest<CreatedAt>() {
    protected lateinit var filter: CreatedAt
    private lateinit var date: Date

    @Before
    fun setup() {
        date = Date()
    }

    private fun assertValues(actual: String) {
        assertValues(filter,"createdAt", actual)
    }

    @Test
    fun greaterThan() {
        filter = CreatedAt.greaterThan(date)
        assertValues("gt(${formatter.format(date)})")
    }

    @Test
    fun lessThan() {
        filter = CreatedAt.lessThan(date)
        assertValues("lt(${formatter.format(date)})")
    }

    @Test
    fun greaterOrEqualsTo() {
        filter = CreatedAt.greaterOrEqualsTo(date)
        assertValues("ge(${formatter.format(date)})")
    }

    @Test
    fun lessOrEqualsTo() {
        filter = CreatedAt.lessOrEqualsTo(date)
        assertValues("le(${formatter.format(date)})")
    }

    @Test
    fun between() {
        val untilDate = Date()
        filter = CreatedAt.between(date, untilDate)
        assertValues("bt(${formatter.format(date)},${formatter.format(untilDate)})")
    }

    @Test
    fun inRange() {
        val secondDate = Date()
        filter = CreatedAt.inRange(arrayOf(date, secondDate))
        assertValues("in(${formatter.format(date)},${formatter.format(secondDate)})")
    }
}