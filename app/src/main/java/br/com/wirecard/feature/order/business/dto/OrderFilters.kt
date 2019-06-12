package br.com.wirecard.feature.order.business.dto

import br.com.wirecard.base.business.dto.AbstractFilters
import br.com.wirecard.base.business.dto.MutableFilters
import br.com.wirecard.base.business.filter.*

class OrderFilters(private val filters: MutableOrderFilters = MutableOrderFilters()): AbstractFilters() {
    companion object {
        fun mutable(): MutableOrderFilters {
            return MutableOrderFilters()
        }
    }

    override fun toString(): String {
        return filters.toString()
    }
}

class MutableOrderFilters: MutableFilters() {
    fun add(filter: CreatedAt) {
        super.addFilter(filter)
    }

    fun add(filter: PaymentMethod) {
        super.addFilter(filter)
    }

    fun add(filter: OrderValue) {
        super.addFilter(filter)
    }

    fun add(filter: OrderStatus) {
        super.addFilter(filter)
    }

    fun immutable(): OrderFilters {
        return OrderFilters(this)
    }
}