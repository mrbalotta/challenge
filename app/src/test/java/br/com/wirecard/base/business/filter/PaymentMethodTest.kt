package br.com.wirecard.base.business.filter

import br.com.wirecard.model.Payment
import org.junit.Test
import java.util.*

class PaymentMethodTest: EnumeratedFilterTest<Payment.Method, PaymentMethod>() {

    override fun createFilter(el: Set<Payment.Method>): PaymentMethod {
        return PaymentMethod.inRange(el)
    }

    override fun createFilter(el: Payment.Method): PaymentMethod {
        return PaymentMethod.inRange(el)
    }

    override fun assertValues(filter: PaymentMethod, actual: String) {
        assertValues(filter,"paymentMethod", actual)
    }

    @Test
    fun `assert paymentMethod value`() {
        assertSingle(enumValues())
    }

    @Test
    fun `assert paymentMethod range`() {
        assertRange(EnumSet.allOf(Payment.Method::class.java))
    }
}