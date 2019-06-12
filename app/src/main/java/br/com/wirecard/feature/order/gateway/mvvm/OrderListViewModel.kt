package br.com.wirecard.feature.order.gateway.mvvm

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import br.com.wirecard.base.business.filter.*
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.feature.order.business.dto.MutableOrderFilters
import br.com.wirecard.feature.order.business.dto.OrderFilters
import br.com.wirecard.feature.order.business.interactor.GetOrderListUseCase
import br.com.wirecard.feature.order.gateway.di.OrderInjector
import br.com.wirecard.model.Order
import br.com.wirecard.model.Payment

class OrderListViewModel(private val mockOrderListUseCase: GetOrderListUseCase? = null): BaseViewModel() {
    companion object {
        const val ORDER_LIST_CHANNEL = "orderlist"
    }

    val filters = MutableOrderFilters()
    private val orderListUseCase by lazy { injectOrderListUseCase() }

    override fun declareChannels() {
        availableChannels.add(ORDER_LIST_CHANNEL)
    }

    fun getOrders(filters: OrderFilters = OrderFilters()) {
        dispatch(ORDER_LIST_CHANNEL, orderListUseCase, filters)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    private fun injectOrderListUseCase(): GetOrderListUseCase {
        return mockOrderListUseCase ?: OrderInjector.injector.orderListUseCase
    }

    fun applyFilters() {
        val immutableFilters = filters.immutable()
        Log.w("ALE", immutableFilters.toString())
        filters.removeAll()
    }
}