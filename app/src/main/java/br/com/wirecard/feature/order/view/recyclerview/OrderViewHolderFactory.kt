package br.com.wirecard.feature.order.view.recyclerview

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import br.com.wirecard.base.view.recyclerview.ViewHolder
import br.com.wirecard.base.view.recyclerview.ViewHolderFactory
import br.com.wirecard.model.Order

open class OrderViewHolderFactory(@LayoutRes private val layoutId: Int): ViewHolderFactory<Order>() {
    override fun create(parent: ViewGroup): ViewHolder<Order> {
        return OrderViewHolder(inflate(layoutId, parent))
    }
}