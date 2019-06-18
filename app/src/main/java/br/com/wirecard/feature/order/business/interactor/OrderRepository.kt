package br.com.wirecard.feature.order.business.interactor

import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderFilters
import br.com.wirecard.model.Order


interface OrderRepository {
    fun getOrders(filters: OrderFilters, limit: Int = 100, offset: Int = 0): OrderList
    fun getOrderById(orderId: String): Order
}