package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.InRange

abstract class EnumeratedFilter<T:Enum<T>>(name: String): Filter<InRange>(name) {
    interface EnumeratedFilterBulder<T:Enum<T>> {
        fun add(el: T)
        fun remove(el: T)
        fun from(el: EnumeratedFilter<T>)
        fun build(): EnumeratedFilter<T>
    }

    protected val values = mutableSetOf<T>()

    protected fun inRange(values: Set<T>) {
        this.values.addAll(values)
        val list: List<String> = values.map { it.name }.toList()
        set(InRange(list))
    }

    protected fun inRange(value: T) {
        this.values.add(value)
        set(InRange(listOf(value.name)))
    }

    fun isEmpty(): Boolean {
        return values.isEmpty()
    }

    abstract fun builder(): EnumeratedFilterBulder<T>
}