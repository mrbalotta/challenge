package br.com.wirecard.feature.order.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.wirecard.R
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.order.gateway.mvvm.OrderDetailViewModel

class OrderDetailFragment: BaseFragment<OrderDetailViewModel>() {
    override fun getViewModelClass(): Class<OrderDetailViewModel> {
        return OrderDetailViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }
}