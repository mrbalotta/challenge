package br.com.wirecard.feature.order.business.interactor

import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.UseCase
import br.com.wirecard.base.business.interactor.ValueOutput
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderListRequest

class GetOrderListUseCase(private val repository: OrderRepository): UseCase<OrderListRequest, OrderList>() {

    override fun guard(param: OrderListRequest?): Boolean {
        return param != null
    }

    override fun execute(param: OrderListRequest?): Output<OrderList> {
        val filters = param!!.orderFilters
        val orders = repository.getOrders(filters, param.limit, param.offset)
        return ValueOutput(orders)
    }
}