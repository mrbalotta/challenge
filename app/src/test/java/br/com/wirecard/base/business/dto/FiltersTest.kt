package br.com.wirecard.base.business.dto

import br.com.wirecard.base.business.delimiter.Between
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FiltersTest {
    lateinit var filters: MutableFilters

    @Before
    fun setup() {
        filters = MutableFilters()
    }

    @Test
    fun givenNoFilters_thenReturnEmpty() {
        assertEquals(filters.toString(), "")
    }

    @Test
    fun whenFilterNameIsEmpty_returnEmpty() {
        filters.add("", Between("", ""))
        assertEquals(filters.toString(), "")
    }

    @Test
    fun whenValueIsNotValid_returnEmpty() {
        filters.add("name", Between("", ""))
        assertEquals(filters.toString(), "")
    }

    @Test
    fun whenFilterNameIsEmptyAndValueIsNotValid_returnEmpty() {
        filters.add("", Between("", ""))
        assertEquals(filters.toString(), "")
    }

    @Test
    fun givenOneValidFilter_thenSeparateNameAndValueWithPairSeparator() {
        filters.add("name", Between("floor", "ceil"))
        assertEquals(filters.toString(), "name::bt(floor,ceil)")
    }

    @Test
    fun givenTwoValidFilters_thenSeparateFiltersWithFilterSeparator() {
        filters.apply {
            add("filter1", Between("floor1", "ceil1"))
            add("filter2", Between("floor2", "ceil2"))
        }
        assertEquals(filters.toString(), "filter1::bt(floor1,ceil1)|filter2::bt(floor2,ceil2)")
    }
}