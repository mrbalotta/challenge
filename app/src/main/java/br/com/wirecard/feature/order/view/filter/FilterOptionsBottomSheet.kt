package br.com.wirecard.feature.order.view.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import br.com.wirecard.R
import br.com.wirecard.base.view.ui.BaseBottomSheet
import br.com.wirecard.feature.order.gateway.mvvm.OrderListViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.dialog_filter_options.*


class FilterOptionsBottomSheet: BaseBottomSheet<OrderListViewModel>() {
    override fun getViewModelClass(): Class<OrderListViewModel> {
        return OrderListViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_filter_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs(view)
        setupOkButton()
    }

    private fun setupOkButton() {
        filterBtnTop.setOnClickListener {
            viewModel.applyFilters()
            dismiss()
        }
    }

    private fun setupTabs(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val pager = setupViewPager(view)
        with(tabLayout) {
            setupWithViewPager(pager)
            tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    private fun setupViewPager(view: View): ViewPager {
        return view.findViewById<ViewPager>(R.id.pager).apply {
            adapter = FilterPagerAdapter(childFragmentManager)
        }
    }
}