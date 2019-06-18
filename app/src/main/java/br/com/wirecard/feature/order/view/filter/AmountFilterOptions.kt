package br.com.wirecard.feature.order.view.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import br.com.wirecard.base.business.filter.OrderValue
import br.com.wirecard.base.view.util.MoneyTextWatcher
import br.com.wirecard.base.view.util.centsParser
import kotlinx.android.synthetic.main.filter_amount_options.*
import java.math.BigDecimal


class AmountFilterOptions : MultiDelimiterFilterOptions() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        hint = getString(br.com.wirecard.R.string.value)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(br.com.wirecard.R.layout.filter_amount_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFieldsListeners()
    }

    private fun setupFieldsListeners() {
        listOf<EditText>(firstTxt, secondTxt).forEach {
            it.apply {
                addTextChangedListener(MoneyTextWatcher(this, ::centsParser))
                setOnEditorActionListener {view,action,_ -> onKeyboardListener(view,action)}
            }
        }
    }

    private fun onKeyboardListener(view: View, action: Int): Boolean {
        if (action == EditorInfo.IME_ACTION_DONE) {
            if(firstTxt.text.isNotEmpty()) {
                val first = firstTxt.tag as BigDecimal
                val second = secondTxt.tag as BigDecimal?
                val delimiter = CurrencyDelimiterHelper(delimiterBuilder).buildDelimiter(first,second)
                val filter = OrderValue().apply{set(delimiter)}
                viewModel.filters.add(filter)
                hideKeyboard(view)
            }
        }

        return true
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
