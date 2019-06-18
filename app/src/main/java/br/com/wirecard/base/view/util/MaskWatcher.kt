package br.com.wirecard.base.view.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import br.com.wirecard.base.business.delimiter.Delimiter.Companion.formatter
import java.lang.ref.WeakReference
import java.math.BigDecimal

class MoneyTextWatcher(editText: EditText, private val parser:(String)->BigDecimal) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> = WeakReference(editText)

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference.get() ?: return
        val s = editable.toString()
        if (s.isEmpty()) return

        editText.removeTextChangedListener(this)
        val cleanString = s.replace("[^0-9]+".toRegex(), "")

        val parsed = parser(cleanString)
        val formatted = currency("", parsed.toDouble())

        with(editText) {
            setText(formatted)
            setSelection(formatted.length)
            addTextChangedListener(this@MoneyTextWatcher)
            tag = parsed
        }
    }
}