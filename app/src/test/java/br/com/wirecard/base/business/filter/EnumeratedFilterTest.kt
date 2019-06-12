package br.com.wirecard.base.business.filter

abstract class EnumeratedFilterTest<E:Enum<E>, T:EnumeratedFilter<E>>: AbstractFilterTest<T>() {

    fun assertSingle(enumeration: Array<E>) {
        for(el in enumeration) {
            val filter = createFilter(el)
            assertValues(filter,"in(${el.name})")
        }
    }

    fun assertRange(enumSet: Set<E>) {
        val filter = createFilter(enumSet)
        assertValues(filter, "in(${enumSet.joinToString(",")})")
    }

    protected abstract fun createFilter(el: E): T
    protected abstract fun createFilter(el: Set<E>): T
    protected abstract fun assertValues(filter: T, actual: String)
}