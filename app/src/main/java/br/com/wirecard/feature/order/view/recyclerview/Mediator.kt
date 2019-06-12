package br.com.wirecard.feature.order.view.recyclerview

import br.com.wirecard.R
import br.com.wirecard.base.view.recyclerview.ProgressFactory
import br.com.wirecard.base.view.recyclerview.ViewHolderFactory
import br.com.wirecard.base.view.recyclerview.ViewHolderFactoryMediator
import br.com.wirecard.model.Order

class Mediator: ViewHolderFactoryMediator<Order>() {
    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_ITEM_LAST = 3

    }

    private val mapping : Map<Int, ViewHolderFactory<Order>>

    init {
        mapping = mapOf(
            VIEW_TYPE_LOADING to ProgressFactory<Order>(),
            VIEW_TYPE_ITEM to OrderViewHolderFactory(R.layout.item_order_list),
            VIEW_TYPE_ITEM_LAST to OrderViewHolderFactory(R.layout.item_order_list/*_last*/)
        )
    }

    override fun getViewType(position: Int, list: List<Order?>): Int {
        return when (position) {
            list.size -> VIEW_TYPE_LOADING
            list.size-1 -> VIEW_TYPE_ITEM_LAST
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun createViewHolderFactory(code: Int): ViewHolderFactory<Order> {
        return mapping[code] ?: ProgressFactory()
    }
}