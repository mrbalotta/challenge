package br.com.wirecard.feature.order.view.di

import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy

interface OrderViewInjector {
    companion object {
        lateinit var injector: OrderViewInjector
    }

    val dateFormatter: DateFormatterStrategy
    val paymentMethodTranslator: PaymentMethodTranslateStrategy
    val orderStatusTranslator: OrderStatusTranslateStrategy
}