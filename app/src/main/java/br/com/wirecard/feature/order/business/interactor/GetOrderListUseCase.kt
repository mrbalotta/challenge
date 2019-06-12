package br.com.wirecard.feature.order.business.interactor

import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.UseCase
import br.com.wirecard.base.business.interactor.ValueOutput
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderFilters

class GetOrderListUseCase(private val repository: OrderRepository): UseCase<OrderFilters, OrderList>() {

    override fun execute(param: OrderFilters?): Output<OrderList> {
        val filters = param ?: OrderFilters()
        val orders = repository.getOrders(filters)
        return ValueOutput(orders)
    }
}