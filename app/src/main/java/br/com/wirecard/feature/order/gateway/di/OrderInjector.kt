package br.com.wirecard.feature.order.gateway.di

import br.com.wirecard.feature.order.business.interactor.GetOrderDetailUseCase
import br.com.wirecard.feature.order.business.interactor.GetOrderListUseCase

interface OrderInjector {
    companion object {
        lateinit var injector: OrderInjector
    }

    val orderListUseCase: GetOrderListUseCase
    val orderDetailUseCase: GetOrderDetailUseCase
}