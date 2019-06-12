package br.com.wirecard.base.business.delimiter

import android.annotation.SuppressLint
import br.com.wirecard.base.business.dto.Either
import java.text.SimpleDateFormat
import java.util.*

abstract class Delimiter {
    companion object {
        @JvmStatic
        @SuppressLint("SimpleDateFormat")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
    }

    protected abstract val pattern: String
    open fun isValid() = true
}

abstract class RangeDelimiter: Delimiter()

abstract class NumberOrDateDelimiter(private val value: Either<Number, Date>): Delimiter() {
    override fun toString(): String {
        return if(value.isLeft()) {
            String.format(pattern, value.left!!.value.toString())
        } else {
            String.format(pattern, formatter.format(value.right!!.value))
        }
    }
}

