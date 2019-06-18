package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.View
import br.com.wirecard.R
import br.com.wirecard.base.business.delimiter.*
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.base.view.util.centsParser
import br.com.wirecard.feature.order.gateway.mvvm.OrderListViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.delimiters.*
import kotlinx.android.synthetic.main.filter_date_options.*
import java.math.BigDecimal
import java.util.*

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

    abstract class DelimiterHelper<T>(val delimiterBuilder: DelimiterBuilder<*>) {

        open fun buildDelimiter(first: T, second: T?): Delimiter? {
            return when (delimiterBuilder) {
                is Between.Builder -> {
                    val builder = delimiterBuilder
                    buildBetween(builder, first, second)
                }
                is InRange.Builder -> {
                    val builder = delimiterBuilder
                    buildInRange(builder, first)
                }
                is NumberOrDateDelimiterBuilder -> {
                    val builder = delimiterBuilder
                    buildNumberOrDateDelimiter(builder, first)
                }
                else -> null
            }
        }

        protected abstract fun buildNumberOrDateDelimiter(builder: NumberOrDateDelimiterBuilder, first: T): Delimiter?

        protected abstract fun buildBetween(builder: Between.Builder, first: T, second: T?): Delimiter?

        protected abstract fun buildInRange(builder: InRange.Builder, first: T): Delimiter?
    }


    open class NumberDelimiterHelper(delimiterBuilder: DelimiterBuilder<*>) : DelimiterHelper<Number>(delimiterBuilder) {

        override fun buildNumberOrDateDelimiter(builder: NumberOrDateDelimiterBuilder, first: Number): Delimiter? {
            return with(builder) {
                add(first)
                build()
            }
        }

        override fun buildBetween(builder: Between.Builder, first: Number, second: Number?): Delimiter? {
            if(second == null) return null
            return with(builder) {
                add(first, second)
                build()
            }
        }

        override fun buildInRange(builder: InRange.Builder, first: Number): Delimiter? {
            return with(builder) {
                add(first)
                build()
            }
        }
    }

    class CurrencyDelimiterHelper(delimiterBuilder: DelimiterBuilder<*>): NumberDelimiterHelper(delimiterBuilder) {
        override fun buildDelimiter(first: Number, second: Number?): Delimiter? {
            val bdFirst = if(first is BigDecimal) centsParser(first) else first
            val bdSecond = if(second != null && second is BigDecimal) centsParser(second) else second
            return super.buildDelimiter(bdFirst, bdSecond)
        }
    }

    class DateDelimiterHelper(delimiterBuilder: DelimiterBuilder<*>) : DelimiterHelper<Date>(delimiterBuilder) {

        override fun buildNumberOrDateDelimiter(builder: NumberOrDateDelimiterBuilder, first: Date): Delimiter? {
            return with(builder) {
                add(first)
                build()
            }
        }

        override fun buildBetween(builder: Between.Builder, first: Date, second: Date?): Delimiter? {
            if(second == null) return null
            return with(builder) {
                add(first, second)
                build()
            }
        }

        override fun buildInRange(builder: InRange.Builder, first: Date): Delimiter? {
            return with(builder) {
                add(first)
                build()
            }
        }
    }
}