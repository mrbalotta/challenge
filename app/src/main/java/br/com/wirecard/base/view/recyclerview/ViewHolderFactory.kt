package br.com.wirecard.base.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import br.com.wirecard.R

abstract class ViewHolderFactoryMediator<P> {
    open fun getViewType(position: Int, list: List<P?>) = 0

    abstract fun createViewHolderFactory(code: Int): ViewHolderFactory<P>

    fun getViewHolderFactory(code: Int): ViewHolderFactory<P> {
        return createViewHolderFactory(code)
    }
}

abstract class ViewHolderFactory<P> {
    abstract fun create(parent: ViewGroup): ViewHolder<P>

    protected fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}

class ProgressFactory<P>: ViewHolderFactory<P>() {
    override fun create(parent: ViewGroup): ViewHolder<P> {
        return ProgressViewHolder(inflate(R.layout.item_progress, parent))
    }
}

class ProgressViewHolder<P>(itemView: View) : ViewHolder<P>(itemView, false)
