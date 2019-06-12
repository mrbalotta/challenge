package br.com.wirecard.feature.order.view.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.wirecard.R
import br.com.wirecard.base.view.recyclerview.ViewHolder
import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy
import br.com.wirecard.base.view.util.currency
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import br.com.wirecard.model.Order
import br.com.wirecard.model.Order.Status.*
import br.com.wirecard.model.Payment.Method.BOLETO
import br.com.wirecard.model.Payment.Method.valueOf as methodValueOf
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_order_list.view.*

class OrderViewHolder(itemView: View): ViewHolder<Order>(itemView) {
    private val paymentMethodTranslator: PaymentMethodTranslateStrategy by lazy { injectPaymentMethodTranslateStrategy() }
    private val orderStatusTranslator: OrderStatusTranslateStrategy by lazy { injectOrderStatusTranslateStrategy() }
    private val formatter: DateFormatterStrategy by lazy {injectDateFormatterStrategy()}

    private val imgPayment: ImageView = itemView.imgPayment
    private val amount: TextView = itemView.amount
    private val email: TextView = itemView.email
    private val date: TextView = itemView.date
    private val ownId: TextView = itemView.ownId
    private val paymentStatus: Chip = itemView.paymentStatus
    override fun bind(item: Order, listener: (Order) -> Unit) {
        amount.text = currency(item.amount.currency, item.amount.total)
        email.text = item.customer.email
        date.text = formatter.format(item.createdAt)
        ownId.text = item.ownId.toUpperCase()
        bindStatus(item)
        bindPaymentMethod(item)
        super.bind(item, listener)
    }

    private fun bindPaymentMethod(item: Order) {
        val drawableId = paymentMethodTranslator
            .translateMethod(item.payments[0].fundingInstrument.method)
            .drawableId
        imgPayment.setImageResource(drawableId ?: 0)
    }

    private fun bindStatus(item: Order) {
        val pair = orderStatusTranslator.translateStatus(item.status).toPair()
        val (name, color) = pair
        paymentStatus.apply {
            text = context.getText(name)
            setChipBackgroundColorResource(color)
        }
    }

    private fun injectDateFormatterStrategy(): DateFormatterStrategy {
        return OrderViewInjector.injector.dateFormatter
    }

    private fun injectPaymentMethodTranslateStrategy(): PaymentMethodTranslateStrategy {
        return OrderViewInjector.injector.paymentMethodTranslator
    }

    private fun injectOrderStatusTranslateStrategy(): OrderStatusTranslateStrategy {
        return OrderViewInjector.injector.orderStatusTranslator
    }
}
