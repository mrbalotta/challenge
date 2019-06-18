package br.com.wirecard.feature.order.gateway.mvvm

import br.com.wirecard.base.business.dto.Pageable
import br.com.wirecard.base.business.interactor.Output
import br.com.wirecard.base.business.interactor.ValueOutput
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.feature.order.business.dto.MutableOrderFilters
import br.com.wirecard.feature.order.business.dto.OrderFilters
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderListRequest
import br.com.wirecard.feature.order.business.interactor.GetOrderListUseCase
import br.com.wirecard.feature.order.gateway.di.OrderInjector

class OrderListViewModel(private val mockOrderListUseCase: GetOrderListUseCase? = null): BaseViewModel() {
    companion object {
        const val ORDER_LIST_CHANNEL = "orderlist"
    }

    val filters = MutableOrderFilters()
    private val orderListUseCase by lazy { injectOrderListUseCase() }
    private lateinit var lastRequest: OrderListRequest
    private var lastResult: Pageable<OrderList>? = null

    override fun declareChannels() {
        availableChannels.add(ORDER_LIST_CHANNEL)
    }

    fun getOrders(filters: OrderFilters = OrderFilters()) {
        lastResult = null
        lastRequest = OrderListRequest(filters)
        dispatch(ORDER_LIST_CHANNEL, orderListUseCase, lastRequest)
    }

    fun getNextOrders() {
        val previous = lastResult!!.wrappedValue
        val request = OrderListRequest(
            orderFilters = lastRequest.orderFilters,
            offset = lastRequest.offset + previous.summary.count,
            limit = lastRequest.limit)
        dispatch(ORDER_LIST_CHANNEL, orderListUseCase, request)
        lastRequest = request
    }

    private fun injectOrderListUseCase(): GetOrderListUseCase {
        return mockOrderListUseCase ?: OrderInjector.injector.orderListUseCase
    }

    fun applyFilters() {
        val immutableFilters = filters.immutable()
        getOrders(immutableFilters)
        filters.removeAll()
    }

    override fun postValue(channelName: String, output: Output<*>) {
        if(output.isSuccess()) {
            lastResult = asPageable(output)
            super.postValue(channelName, ValueOutput(lastResult))
        } else {
            super.postValue(channelName, output)
        }
    }

    private fun asPageable(output: Output<*>): Pageable<OrderList> {
        val value = output.value as OrderList
        return if (lastResult != null) {
            val last = lastResult
            val hasMoreItems = value.summary.count == lastRequest.limit
            val page = last!!.page + 1
            Pageable(page = page, wrappedValue = value, hasMoreItems = hasMoreItems)
        } else {
            Pageable(wrappedValue = value)
        }
    }
}