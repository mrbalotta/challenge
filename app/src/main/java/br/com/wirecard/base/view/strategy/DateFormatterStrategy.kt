package br.com.wirecard.base.view.strategy

import java.util.*

interface DateFormatterStrategy {
    fun format(date: String): String
    fun format(year: Int, monthOfYear: Int, dayOfMonth: Int): String
    fun parse(year: Int, monthOfYear: Int, dayOfMonth: Int): Date
}