package br.com.wirecard.feature.order.gateway.mvvm

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.feature.login.gateway.mvvm.LoginViewModel.Companion.LOGIN_CHANNEL
import br.com.wirecard.feature.order.business.interactor.GetOrderDetailUseCase
import br.com.wirecard.feature.order.gateway.di.OrderInjector

class OrderDetailViewModel(private val mockLoginUseCase: GetOrderDetailUseCase? = null): BaseViewModel() {
    companion object {
        const val ORDER_DETAIL_CHANNEL = "orderdetail"
    }

    private val orderDetailUseCase by lazy { injectOrderDetailUseCase() }

    override fun declareChannels() {
        availableChannels.add(ORDER_DETAIL_CHANNEL)
    }

    fun getOrderDetail(orderId: String) {
        dispatch(ORDER_DETAIL_CHANNEL, orderDetailUseCase, orderId)
    }

    @VisibleForTesting(otherwise = PRIVATE)
    private fun injectOrderDetailUseCase(): GetOrderDetailUseCase {
        return mockLoginUseCase ?: OrderInjector.injector.orderDetailUseCase
    }
}
