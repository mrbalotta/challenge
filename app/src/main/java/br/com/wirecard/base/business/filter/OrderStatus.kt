package br.com.wirecard.base.business.filter

import br.com.wirecard.model.Order

class OrderStatus: EnumeratedFilter<Order.Status>("status") {
    companion object {

        fun inRange(values: Set<Order.Status>): OrderStatus {
            return OrderStatus().apply { inRange(values) }
        }
        fun inRange(value: Order.Status): OrderStatus {
            return OrderStatus().apply { inRange(value) }
        }

    }

    override fun builder(): EnumeratedFilterBulder<Order.Status> {
        return Builder()
    }

    class Builder: EnumeratedFilterBulder<Order.Status> {
        private val values = mutableSetOf<Order.Status>()

        override fun from(el: EnumeratedFilter<Order.Status>) {
            val orderStatus = el as OrderStatus
            values.addAll(orderStatus.values)
        }

        override fun add(el: Order.Status) {
            values.add(el)
        }

        override fun remove(el: Order.Status) {
            values.remove(el)
        }

        override fun build(): OrderStatus {
            return inRange(values)
        }
    }
}