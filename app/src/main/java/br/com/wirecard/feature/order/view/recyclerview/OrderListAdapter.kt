package br.com.wirecard.feature.order.view.recyclerview

import br.com.wirecard.base.view.recyclerview.*
import br.com.wirecard.model.Order

class OrderListAdapter(listener: (Order)->Unit): Adapter<Order>(listener=listener) {
    private var hasMoreItems: Boolean = true
    var total: Int = 0

    override fun getFactoryMediator(): ViewHolderFactoryMediator<Order> {
        return Mediator()
    }

    override fun getItemCount(): Int {
        return when {
            hasMoreItems && list.size < total -> list.size + 1
            else -> list.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<Order>, position: Int) {
        if(position < list.size && list[position] != null) holder.bind(list[position]!!, listener)
    }

    fun noMoreItems() {
        hasMoreItems = false
        notifyDataSetChanged()
    }
}

