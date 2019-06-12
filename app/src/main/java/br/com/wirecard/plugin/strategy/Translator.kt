package br.com.wirecard.plugin.strategy

import br.com.wirecard.R
import br.com.wirecard.base.view.strategy.OrderStatusTranslate
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.base.view.strategy.PaymentMethodTranslate
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy
import br.com.wirecard.model.Order
import br.com.wirecard.model.Payment

class Translator: PaymentMethodTranslateStrategy, OrderStatusTranslateStrategy {
    override fun translateStatus(status: String): OrderStatusTranslate {
        return translateStatus(Order.Status.valueOf(status))
    }

    override fun translateStatus(status: Order.Status): OrderStatusTranslate {
        return when (status) {
            Order.Status.PAID -> OrderStatusTranslate(R.string.paid, R.color.malachite)
            Order.Status.REVERTED -> OrderStatusTranslate(R.string.reverted, R.color.violet)
            Order.Status.NOT_PAID -> OrderStatusTranslate(R.string.not_paid, R.color.granadier)
            Order.Status.WAITING -> OrderStatusTranslate(R.string.waiting, R.color.buddha_gold)
        }
    }

    override fun translateMethod(method: Payment.Method): PaymentMethodTranslate {
        return when (method) {
            Payment.Method.BOLETO ->
                PaymentMethodTranslate(nameId = R.string.boleto, drawableId = R.drawable.ic_barcode)
            Payment.Method.CREDIT_CARD ->
                PaymentMethodTranslate(nameId = R.string.credit_card, drawableId = R.drawable.ic_payment_card)
            Payment.Method.DEBIT_CARD ->
                PaymentMethodTranslate(nameId = R.string.debit_card, drawableId = R.drawable.ic_payment_card)
            Payment.Method.ONLINE_BANK_DEBIT ->
                PaymentMethodTranslate(nameId = R.string.online_bank_debit, drawableId = R.drawable.ic_payment_card)
            Payment.Method.ONLINE_BANK_FINANCING ->
                PaymentMethodTranslate(nameId = R.string.online_bank_financing, drawableId = R.drawable.ic_payment_card)
            Payment.Method.WALLET ->
                PaymentMethodTranslate(nameId = R.string.wallet, drawableId = R.drawable.ic_payment_card)
        }
    }

    override fun translateMethod(method: String): PaymentMethodTranslate {
        return translateMethod(Payment.Method.valueOf(method))
    }
}