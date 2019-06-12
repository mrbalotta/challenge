package br.com.wirecard.plugin.repository

import br.com.wirecard.base.business.filter.CreatedAt
import br.com.wirecard.feature.order.business.dto.OrderFilters
import com.nhaarman.mockitokotlin2.spy
import org.junit.Test
import java.util.*

class OrderRepositoryImplTest: LoggedInRepositoryTest<OrderRepositoryImpl>() {

    override fun setupRepository(httpUrl: String) {
        repository = spy(OrderRepositoryImpl(httpUrl, sessionDAO))
    }

    @Test
    fun `when filter is createAt, assert path`() {
        val date = Date()
        val createdAt = CreatedAt.inRange(arrayOf(date))
        val filters = with(OrderFilters.mutable()) {
            add(createdAt)
            immutable()
        }

        enqueueEmptyResponse(200)

        repository.getOrders(filters)
        val request = server.takeRequest()

    }
}