package br.com.wirecard.feature.order.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.wirecard.R
import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.base.view.util.currency
import br.com.wirecard.feature.order.gateway.mvvm.OrderDetailViewModel
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import br.com.wirecard.model.Order
import kotlinx.android.synthetic.main.fragment_order_detail.*

class OrderDetailFragment: BaseFragment<OrderDetailViewModel>() {
    private val args: OrderDetailFragmentArgs by navArgs()
    private val paymentMethodTranslator: PaymentMethodTranslateStrategy by lazy { injectPaymentMethodTranslateStrategy() }
    private val orderStatusTranslator: OrderStatusTranslateStrategy by lazy { injectOrderStatusTranslateStrategy() }
    private val formatter: DateFormatterStrategy by lazy {injectDateFormatterStrategy()}

    override fun getViewModelClass(): Class<OrderDetailViewModel> {
        return OrderDetailViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrderDetail(args.orderId)
    }

    override fun handleSuccess(value: Any?) {
        if(value is Order) {
            handleOrderDetail(value)
        }
    }

    private fun handleOrderDetail(order: Order) {
        setOrderId(order)
        setOperationType(order)
        setTransaction(order)
        setCreatedAt(order)
        setStatus(order)
        setCustomer(order)
        setPayment(order)
    }

    private fun setCustomer(order: Order) {
        buyer.text = order.customer.fullname
        buyerEmail.text = order.customer.email
    }

    private fun setPayment(order: Order) {
        totalPayments.text = order.payments.size.toString()
        amount.text = currency(order.amount.currency, order.amount.total)
        amountDescription.text = currency("+", order.amount.total)
        tax.text = currency("-", order.amount.fees)
        liquid.text = currency("", order.amount.liquid)
    }

    private fun setStatus(order: Order) {
        val translation = orderStatusTranslator.translateStatus(order.status)
        status.text = getString(translation.nameId)
        statusDate.text = formatter.format(order.updatedAt)
    }

    private fun setTransaction(order: Order) {
        transactionId.text = order.ownId
    }

    private fun setOperationType(order: Order) {
        val translation = paymentMethodTranslator.translateMethod(order.payments[0].fundingInstrument.method)
        operationType.setTextColor(requireContext().resources.getColor(translation.colorId!!))
        operationType.text = getString(translation.nameId)
    }

    private fun setOrderId(order: Order) {
        ownId.text = order.id
    }

    private fun setCreatedAt(order: Order) {
        createdAt.text = formatter.format(order.createdAt)
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
