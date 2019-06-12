package br.com.wirecard.feature.order.business.interactor

import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.UseCase
import br.com.wirecard.base.business.interactor.ValueOutput
import br.com.wirecard.model.Order

class GetOrderDetailUseCase(private val repository: OrderRepository): UseCase<String, Order>() {

    override fun guard(param: String?): Boolean {
        return param != null
    }

    override fun execute(param: String?): Output<Order> {
        val order = repository.getOrderById(param!!)
        return ValueOutput(order)
    }
}