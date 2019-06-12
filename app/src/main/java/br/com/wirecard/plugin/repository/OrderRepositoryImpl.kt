package br.com.wirecard.plugin.repository

import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderFilters
import br.com.wirecard.feature.order.business.interactor.OrderRepository
import br.com.wirecard.model.Order
import br.com.wirecard.plugin.dao.SessionDAO

class OrderRepositoryImpl(baseUrl: String, dao: SessionDAO): LoggedInRepository(baseUrl, dao), OrderRepository {
    override fun getOrders(filters: OrderFilters, limit: Int, offset: Int): OrderList {
        val call = getService().getOrders(filters, limit, offset)
        return getBodyOrThrow(call)
    }

    override fun getOrderById(orderId: String): Order {
        val call = getService().getOrderById(orderId)
        return getBodyOrThrow(call)
    }
}