package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wirecard.R
import br.com.wirecard.base.business.delimiter.InRange
import br.com.wirecard.base.business.filter.OrderStatus
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import br.com.wirecard.model.Order
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.filter_enumerated_options.*

class PaymentStatusFilterOptions: EnumFilterOptions() {
    private val orderStatusTranslator: OrderStatusTranslateStrategy by lazy { injectOrderStatusTranslateStrategy() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_enumerated_options, container, false)
    }

    override fun setupChips() {
        for (status in Order.Status.values()) {
            val chip = Chip(requireContext()).apply {
                setChipDrawable(ChipDrawable.createFromAttributes(context, null, 0, R.style.ChipChoice))
                setOnCheckedChangeListener(::onChipClickListener)
                text = getString(orderStatusTranslator.translateStatus(status).nameId)
                tag = status
            }
            group.addView(chip)
        }
    }

    private fun onChipClickListener(btn: View, checked: Boolean) {
        if(checked) viewModel.filters.add(OrderStatus.inRange(btn.tag as Order.Status))
    }

    private fun injectOrderStatusTranslateStrategy(): OrderStatusTranslateStrategy {
        return OrderViewInjector.injector.orderStatusTranslator
    }
}