package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.InRange
import org.junit.Assert.*
import org.junit.Test

class RangeDelimiterTest {
    @Test
    fun givenFilterInRange_thenPatternEqualsToIn() {
        val delimiter = InRange(listOf("1", "2", "3", "4"))
        assertEquals(delimiter.toString(), "in(1,2,3,4)")
    }
}