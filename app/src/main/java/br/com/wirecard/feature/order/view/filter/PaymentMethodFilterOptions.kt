package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.wirecard.R
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import br.com.wirecard.model.Payment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.filter_enumerated_options.*

class PaymentMethodFilterOptions: EnumFilterOptions() {
    private val paymentMethodTranslator: PaymentMethodTranslateStrategy by lazy { injectPaymentMethodTranslateStrategy() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_enumerated_options, container, false)
    }

    override fun setupChips() {
        for (method in Payment.Method.values()) {
            val chip = Chip(requireContext()).apply {
                setChipDrawable(ChipDrawable.createFromAttributes(context, null, 0, R.style.ChipChoice))
                text = getString(paymentMethodTranslator.translateMethod(method).nameId)
            }
            group.addView(chip)
        }
    }

    private fun injectPaymentMethodTranslateStrategy(): PaymentMethodTranslateStrategy {
        return OrderViewInjector.injector.paymentMethodTranslator
    }
}