package br.com.wirecard.base.business.filter

import br.com.wirecard.model.Payment

class PaymentMethod: EnumeratedFilter<Payment.Method>("paymentMethod") {
    companion object {

        fun inRange(values: Set<Payment.Method>): PaymentMethod {
            return PaymentMethod().apply { inRange(values) }
        }
        fun inRange(value: Payment.Method): PaymentMethod {
            return PaymentMethod().apply { inRange(value) }
        }

    }

    override fun builder(): EnumeratedFilterBulder<Payment.Method> {
        return Builder()
    }

    class Builder: EnumeratedFilterBulder<Payment.Method> {
        private val values = mutableSetOf<Payment.Method>()

        override fun from(el: EnumeratedFilter<Payment.Method>) {
            val paymentMethod = el as PaymentMethod
            values.addAll(paymentMethod.values)
        }

        override fun add(el: Payment.Method) {
            values.add(el)
        }

        override fun remove(el: Payment.Method) {
            values.remove(el)
        }

        override fun build(): PaymentMethod {
            return inRange(values)
        }
    }
}