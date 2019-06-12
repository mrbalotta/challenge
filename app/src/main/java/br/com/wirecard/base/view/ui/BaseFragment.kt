package br.com.wirecard.base.view.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.wirecard.R
import br.com.wirecard.base.business.exception.HttpException
import br.com.wirecard.base.business.exception.InternetConnectionException
import br.com.wirecard.base.gateway.mvvm.BaseViewModel
import br.com.wirecard.base.gateway.mvvm.ViewState

abstract class BaseFragment<V: BaseViewModel>: Fragment() {
    protected lateinit var viewModel: V
    protected open val activityScoped = false

    protected abstract fun getViewModelClass(): Class<V>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews(view)
        observeViewModel()
    }

    protected open fun setupViews(view: View) {}

    protected open fun setupViewModel() {
        val clazz = getViewModelClass()
        viewModel = if(activityScoped) {
            ViewModelProviders.of(requireActivity()).get(clazz)
        } else {
            ViewModelProviders.of(this).get(clazz)
        }
    }

    protected open fun observeViewModel() {
        observeAllChannels()
    }

    protected fun observeAllChannels() {
        viewModel.getChannels().forEach { observeChannel(it) }
    }

    protected fun observeChannel(channelName: String) {
        viewModel.observe(channelName,this, Observer { v-> handleResponse(v) })
    }

    protected open fun handleResponse(state: ViewState) {
        if(!state.handled)  {
            hideLoading()
            state.handled = true
            if (state.isError()) {
                handleThrowable(state.output.error)
            } else {
                handleSuccess(state.output.value)
            }
        }
    }

    protected open fun showLoading() {}

    protected open fun hideLoading() {}

    private fun handleThrowable(error: Throwable?) {
        when(error) {
            is HttpException -> handleHttpError(error)
            is InternetConnectionException -> handleConnectionError()
            else -> handleError(error)
        }
    }
    protected open fun handleHttpError(error: HttpException) {}

    protected open fun handleConnectionError() {}

    protected open fun handleError(error: Throwable?) {}

    protected open fun handleSuccess(value: Any?) {}

    protected fun reveal(view: View) {
        if(view.visibility != View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
            view.visibility = View.VISIBLE
        }
    }

    protected fun setupToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = false) {
        (activity as? BaseActivity)?.resetToolbar(toolbar, homeAsUpEnabled)
    }
}