package br.com.wirecard.feature.order.gateway.mvvm

import br.com.wirecard.base.gateway.mvvm.BaseViewModelTest
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.interactor.GetOrderListUseCase
import br.com.wirecard.feature.order.business.interactor.OrderRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class OrderListViewModelTest: BaseViewModelTest<OrderListViewModel>() {
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
    fun `when getOrders throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.getOrders(any(), any(), any())).thenThrow(exception)
        doGetOrders()
        assertViewStateError(exception)
    }

    @Test
    fun `when getOrders succeeds, assert callbacks`() {
        val orderList = mock<OrderList>()
        whenever(repository.getOrders(any(), any(), any())).thenReturn(orderList)
        doGetOrders()
        assertViewStateSuccess(orderList)
    }

    private fun doGetOrders() {
        runBlocking { viewModel.getOrders() }
    }

    override fun setupViewModel() {
        val useCase = GetOrderListUseCase(repository).apply { testMode = true }
        viewModel = spy(OrderListViewModel(useCase))
    }
}