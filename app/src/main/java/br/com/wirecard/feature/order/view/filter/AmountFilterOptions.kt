package br.com.wirecard.feature.order.view.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.wirecard.R
import br.com.wirecard.base.view.util.MoneyTextWatcher
import kotlinx.android.synthetic.main.filter_amount_options.*

class AmountFilterOptions : MultiDelimiterFilterOptions() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        hint = getString(R.string.value)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_amount_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFieldsForCurrency()
    }

    private fun setupFieldsForCurrency() {
        listOf<EditText>(firstTxt, secondTxt).forEach {
            it.apply {
                addTextChangedListener(MoneyTextWatcher(this))
                setOnClickListener { }
            }
        }
    }
}
