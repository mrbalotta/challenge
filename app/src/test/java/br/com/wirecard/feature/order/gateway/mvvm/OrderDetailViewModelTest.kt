package br.com.wirecard.feature.order.gateway.mvvm

import br.com.wirecard.base.gateway.mvvm.BaseViewModelTest
import br.com.wirecard.feature.order.business.interactor.GetOrderDetailUseCase
import br.com.wirecard.feature.order.business.interactor.OrderRepository
import br.com.wirecard.model.Order
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OrderDetailViewModelTest: BaseViewModelTest<OrderDetailViewModel>() {
    private lateinit var repository: OrderRepository

    @Before
    override fun setup() {
        repository = mock()
        super.setup()
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `when getOrderById throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.getOrderById(any())).thenThrow(exception)
        doGetDetail()
        assertViewStateError(exception)
    }

    @Test
    fun `when getOrderById succeeds, assert callbacks`() {
        val order = mock<Order>()
        whenever(repository.getOrderById(any())).thenReturn(order)
        doGetDetail()
        assertViewStateSuccess(order)
    }

    private fun doGetDetail() {
        runBlocking {
            viewModel.getOrderDetail("orderId")
        }
    }

    override fun setupViewModel() {
        val useCase = GetOrderDetailUseCase(repository).apply { testMode = true }
        viewModel = spy(OrderDetailViewModel(useCase))
    }
}