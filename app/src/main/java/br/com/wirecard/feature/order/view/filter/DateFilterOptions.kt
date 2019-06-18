package br.com.wirecard.feature.order.view.filter

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.wirecard.R
import br.com.wirecard.base.business.delimiter.Between
import br.com.wirecard.base.business.delimiter.Delimiter
import br.com.wirecard.base.business.delimiter.InRange
import br.com.wirecard.base.business.delimiter.NumberOrDateDelimiterBuilder
import br.com.wirecard.base.business.filter.*
import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import kotlinx.android.synthetic.main.filter_date_options.*
import java.util.*

class DateFilterOptions: MultiDelimiterFilterOptions() {
    private val formatter: DateFormatterStrategy by lazy {injectDateFormatterStrategy()}

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        hint = getString(R.string.date)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.filter_date_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFieldsForDate()
    }

    private fun setupFieldsForDate() {
        listOf<TextView>(firstTxt, secondTxt).forEach { txt ->
            txt.apply {
                setOnClickListener { showDatePicker(it as TextView) }
            }
        }
    }

    private fun showDatePicker(view: TextView) {
        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            view.text = formatter.format(year, monthOfYear, dayOfMonth)
            view.tag = formatter.parse(year, monthOfYear, dayOfMonth)
            onDateSetListener()
        }, currentYear, month, day)
        dpd.show()
    }

    private fun onDateSetListener() {
        val firstDate = firstTxt.tag as Date? ?: Date()
        val secondDate = secondTxt.tag as Date? ?: Date()
        val delimiter = DateDelimiterHelper(delimiterBuilder).buildDelimiter(firstDate, secondDate)
        val filter = CreatedAt().apply{set(delimiter)}
        viewModel.filters.add(filter)
    }

    private fun injectDateFormatterStrategy(): DateFormatterStrategy {
        return OrderViewInjector.injector.dateFormatter
    }
}
