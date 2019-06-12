package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.business.dto.Either
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class NumberDelimiterTest {
    val number = 10
    val either: Either<Number,Date> = Either.left(number)

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

    fun assertion(delimiter: Delimiter, el: String) {
        assertEquals(delimiter.toString(), "$el($number)")
    }
}
