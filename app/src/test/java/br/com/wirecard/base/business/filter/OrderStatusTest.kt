package br.com.wirecard.base.business.filter

import br.com.wirecard.model.Order
import org.junit.Test
import java.util.*

class OrderStatusTest: EnumeratedFilterTest<Order.Status, OrderStatus>() {

    override fun createFilter(el: Set<Order.Status>): OrderStatus {
        return OrderStatus.inRange(el)
    }

    override fun createFilter(el: Order.Status): OrderStatus {
        return OrderStatus.inRange(el)
    }

    override fun assertValues(filter: OrderStatus, actual: String) {
        assertValues(filter,"status", actual)
    }

    @Test
    fun `assert orderStatus value`() {
        assertSingle(enumValues())
    }

    @Test
    fun `assert orderStatus range`() {
        assertRange(EnumSet.allOf(Order.Status::class.java))
    }
}