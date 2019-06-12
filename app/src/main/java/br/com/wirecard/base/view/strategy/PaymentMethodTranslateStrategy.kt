package br.com.wirecard.base.view.strategy

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.wirecard.model.Payment

data class PaymentMethodTranslate(@StringRes val nameId: Int,
                                @DrawableRes val drawableId: Int? = null,
                                @ColorRes val colorId: Int? = null)

interface PaymentMethodTranslateStrategy {
    fun translateMethod(method: Payment.Method): PaymentMethodTranslate
    fun translateMethod(method: String): PaymentMethodTranslate
}