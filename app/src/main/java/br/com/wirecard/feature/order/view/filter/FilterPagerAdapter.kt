package br.com.wirecard.feature.order.view.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FilterPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> DateFilterOptions()
            1 -> AmountFilterOptions()
            2 -> PaymentMethodFilterOptions()
            else -> PaymentStatusFilterOptions()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Data"
            1 -> "Valor"
            2 -> "Meio"
            else -> "Status"
        }
    }
}