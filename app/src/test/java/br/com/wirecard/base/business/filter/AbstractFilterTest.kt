package br.com.wirecard.base.business.filter

import org.junit.Assert.*

abstract class AbstractFilterTest<T:Filter<*>> {
    protected fun assertValues(filter: T, filterName: String, actual: String) {
        val(name, value) = filter.get()
        assertEquals(name, filterName)
        assertEquals(name, filter.name)
        assertEquals(value.toString(), actual)
    }
}