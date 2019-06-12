package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.View
import br.com.wirecard.R
import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.order.gateway.mvvm.OrderListViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.delimiters.*
import kotlinx.android.synthetic.main.filter_date_options.*

abstract class MultiDelimiterFilterOptions: BaseFragment<OrderListViewModel>() {
    protected lateinit var delimiterBuilder: DelimiterBuilder<*>
    protected lateinit var hint: String
    override val activityScoped: Boolean = true

    override fun getViewModelClass(): Class<OrderListViewModel> {
        return OrderListViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChipGroup()
    }

    private fun setupChipGroup() {
        val map = getChipMap()

        for(entry in map.entries) {
            delimiters.addView(createChip(entry))
        }
    }

    private fun createChip(entry: Map.Entry<Int, Pair<DelimiterBuilder<*>,()->Unit>>): Chip {
        return Chip(requireContext()).apply {
            setChipDrawable(ChipDrawable.createFromAttributes(context, null, 0, R.style.ChipChoice))
            setOnClickListener {
                cleanFields()
                delimiterBuilder = entry.value.first
                entry.value.second.invoke()
            }
            text = getString(entry.key)
        }
    }

    private fun getChipMap(): Map<Int, Pair<DelimiterBuilder<*>,()->Unit>> {
        return mapOf(R.string.greater_than to Pair(GreaterThan.Builder(),::setupFirstInputOnly),
                     R.string.greater_equal_to to Pair(GreaterOrEqualsTo.Builder(),::setupFirstInputOnly),
                     R.string.less_than to Pair(LessThan.Builder(), ::setupFirstInputOnly),
                     R.string.less_equal_to to Pair(LessOrEqualsTo.Builder(),::setupFirstInputOnly),
                     R.string.between to Pair(Between.Builder(),::setupBothInputs),
                     R.string.in_range to Pair(InRange.Builder(), ::setupFirstInputOnly))
    }

    private fun setupBothInputs() {
        firstTxt.apply {
            visibility = View.VISIBLE
            hint = context.getString(R.string.from)
        }
        secondTxt.apply {
            visibility = View.VISIBLE
            hint = context.getString(R.string.to)
        }
    }

    private fun setupFirstInputOnly() {
        setupFirstInputOnly(hint)
    }

    private fun setupFirstInputOnly(hintString: String) {
        firstTxt.apply {
            visibility = View.VISIBLE
            hint = hintString
        }
        secondTxt.visibility = View.GONE
    }

    private fun cleanFields() {
        firstTxt.text = ""
        secondTxt.text = ""
    }
}