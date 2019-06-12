package br.com.wirecard.plugin.strategy

import android.annotation.SuppressLint
import br.com.wirecard.base.business.delimiter.Delimiter
import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import java.text.SimpleDateFormat
import java.util.*

class DateFormatterStrategyImpl: DateFormatterStrategy {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("dd/MM/yyyy")

    override fun parse(year: Int, monthOfYear: Int, dayOfMonth: Int): Date {
        val cal = Calendar.getInstance().apply {
            set(year, monthOfYear, dayOfMonth)
        }
        return Date(cal.timeInMillis)
    }

    override fun format(year: Int, monthOfYear: Int, dayOfMonth: Int): String {
        return format(parse(year, monthOfYear, dayOfMonth))
    }

    override fun format(date: String): String {
        val date = Delimiter.formatter.parse(date)
        return formatter.format(date)
    }

    private fun format(date: Date): String {
        return formatter.format(date)
    }
}