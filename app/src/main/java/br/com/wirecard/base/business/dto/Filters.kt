package br.com.wirecard.base.business.dto

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import br.com.wirecard.base.business.delimiter.Delimiter
import br.com.wirecard.base.business.filter.Filter

abstract class AbstractFilters {
    protected val map: MutableMap<String, Delimiter> = mutableMapOf()
    private val filterDelimiter = "|"
    private val pairDelimiter = "::"

    override fun toString(): String {
        var filters = ""
        for(entry in  map.entries) {
            filters += "${entry.key}$pairDelimiter${entry.value}$filterDelimiter"
        }
        return filters.reversed().replaceFirst(filterDelimiter, "").reversed()
    }
}

open class MutableFilters: AbstractFilters() {
    @VisibleForTesting(otherwise = PROTECTED)
    fun add(filter: String, delimiter: Delimiter?) {
        if(filter.isNotEmpty() && delimiter?.isValid() == true) map[filter] = delimiter
    }

    protected fun addFilter(filter: Filter<*>) {
        val (name, delimiter) = filter.get()
        add(name, delimiter)
    }

    @VisibleForTesting(otherwise = PROTECTED)
    fun remove(filter: String) {
        map.remove(filter)
    }

    fun remove(filter: Filter<*>) {
        map.remove(filter.name)
    }

    fun removeAll() {
        map.clear()
    }
}