package br.com.wirecard.base.view.util

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

fun tint(drawable: Drawable, @ColorInt color: Int, mode: PorterDuff.Mode) {
    if (Build.VERSION.SDK_INT >= 21) {
        DrawableCompat.setTint(drawable, color)
        DrawableCompat.setTintMode(drawable, mode)
    } else {
        drawable.mutate().setColorFilter(color, mode)
    }
}

fun currency(currency: String, value: Double): String {
    return "$currency ${String.format("%.2f", value).replace(".", ",")}"
}

fun currency(currency: String, value: Int): String {
    return currency(currency, (value/100).toDouble())
}