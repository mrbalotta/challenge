package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.View
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.order.gateway.mvvm.OrderListViewModel

abstract class EnumFilterOptions: BaseFragment<OrderListViewModel>() {
    override val activityScoped: Boolean = true

    override fun getViewModelClass(): Class<OrderListViewModel> {
        return OrderListViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChips()
    }

    protected abstract fun setupChips()
}