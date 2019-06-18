package br.com.wirecard.feature.order.business.dto

import br.com.wirecard.base.business.delimiter.Delimiter
import br.com.wirecard.base.business.delimiter.InRange
import br.com.wirecard.base.business.dto.AbstractFilters
import br.com.wirecard.base.business.dto.MutableFilters
import br.com.wirecard.base.business.filter.*

class OrderFilters(filters: Map<String, Delimiter> = emptyMap()): AbstractFilters() {
    init {
        for(entry in filters.entries) {
            map[entry.key] = entry.value
        }
    }

    companion object {
        fun mutable(): MutableOrderFilters {
            return MutableOrderFilters()
        }
    }

    fun isEmpty(): Boolean {
        return map.isEmpty()
    }
}

class MutableOrderFilters: MutableFilters() {
    fun add(filter: CreatedAt) {
        super.addFilter(filter)
    }

    fun add(filter: PaymentMethod) {
        if(map.containsKey(filter.name)) {
            extractToRange(filter)
        } else super.addFilter(filter)
    }

    fun add(filter: OrderValue) {
        super.addFilter(filter)
    }

    fun add(filter: OrderStatus) {
        if(map.containsKey(filter.name)) {
            extractToRange(filter)
        } else super.addFilter(filter)
    }

    private fun extractToRange(filter: EnumeratedFilter<*>) {
        val el: InRange = map[filter.name] as InRange
        val range = el.range.toMutableList()
        val (_, value) = filter.get()
        range.addAll((value as InRange).range)
        map[filter.name] = InRange(range)
    }

    fun immutable(): OrderFilters {
        return OrderFilters(map)
    }
}

data class OrderListRequest(
    val orderFilters: OrderFilters = OrderFilters(),
    val offset: Int = 0,
    val limit: Int = 100)