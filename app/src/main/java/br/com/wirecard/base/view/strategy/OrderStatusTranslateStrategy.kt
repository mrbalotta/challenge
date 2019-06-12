package br.com.wirecard.base.view.strategy

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import br.com.wirecard.model.Order


data class OrderStatusTranslate(@StringRes val nameId: Int, @ColorRes val colorId: Int) {
    fun toPair() = Pair(nameId, colorId)
}

interface OrderStatusTranslateStrategy {
    fun translateStatus(status: Order.Status): OrderStatusTranslate
    fun translateStatus(status: String): OrderStatusTranslate
}