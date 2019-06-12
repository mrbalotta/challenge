package br.com.wirecard.feature.order.view.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.wirecard.R
import br.com.wirecard.base.view.recyclerview.InfiniteScrollListener
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.gateway.mvvm.OrderListViewModel
import br.com.wirecard.feature.order.view.filter.FilterOptionsBottomSheet
import br.com.wirecard.feature.order.view.recyclerview.OrderListAdapter
import br.com.wirecard.model.Order
import kotlinx.android.synthetic.main.fragment_order_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*

class OrderListFragment: BaseFragment<OrderListViewModel>() {
    private val scrollListener = InfiniteScrollListener(::getNextOrders)
    private val adapter = OrderListAdapter(::onOrderSelectionListener)
    override val activityScoped: Boolean = true

    override fun getViewModelClass(): Class<OrderListViewModel> {
        return OrderListViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrders()
    }

    override fun setupViews(view: View) {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setupToolbar(toolbar)
        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        orderList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = this@OrderListFragment.adapter
            addOnScrollListener(scrollListener)
        }
    }

    override fun handleSuccess(value: Any?) {
        if(value is OrderList) handleOrderList(value)
    }

    override fun handleError(error: Throwable?) {
        Log.e("ORDERLIST", "Error", error)
    }

    private fun handleOrderList(value: OrderList) {
        Log.w("ORDERLIST", "handleOrderList")
        adapter.total += value.summary.count
        scrollListener.setLoaded()
        value.orders.forEach {adapter.add(it)}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> showFilterOptions()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterOptions(): Boolean {
        val filterOptionsDialog = FilterOptionsBottomSheet()
        filterOptionsDialog.show(fragmentManager, filterOptionsDialog.tag)
        return true
    }

    private fun getNextOrders() {
        Log.w("ORDERLIST", "getNextOrders")
    }

    private fun onOrderSelectionListener(order: Order) {
        Log.w("ORDERLIST", "Order selected #${order.id}")
    }
}
