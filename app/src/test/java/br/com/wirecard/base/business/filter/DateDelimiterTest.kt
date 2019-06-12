package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.business.dto.Either
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class DateDelimiterTest {
    val year = 2019
    val day = 20
    lateinit var calendar: Calendar
    lateinit var either: Either<Number, Date>

    @Before
    fun setup() {
        calendar = prepareCalendar(year, day)
        either = Either.right(calendar.time)
    }

    fun assertion(delimiter: Delimiter, el: String) {
        Assert.assertEquals(delimiter.toString(), "$el($year-05-$day)")
    }

    @Test
    fun givenFilterGreaterThan_thenPatternEqualsToGt() {
        val delimiter = GreaterThan(either)
        assertion(delimiter, "gt")
    }

    @Test
    fun givenFilterLessThan_thenPatternEqualsToLt() {
        val delimiter = LessThan(either)
        assertion(delimiter, "lt")
    }

    @Test
    fun givenFilterLessOrEqualsTo_thenPatternEqualsToLe() {
        val delimiter = LessOrEqualsTo(either)
        assertion(delimiter, "le")
    }

    @Test
    fun givenFilterGreaterOrEqualsTo_thenPatternEqualsToGe() {
        val delimiter = GreaterOrEqualsTo(either)
        assertion(delimiter, "ge")
    }

    private fun prepareCalendar(year: Int, day: Int): Calendar {
        val calendar = Calendar.getInstance()
        val month = Calendar.MAY

        return calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
    }
}