package br.com.wirecard.base.gateway.mvvm

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<T:BaseViewModel> {
    protected lateinit var viewModel: T
    protected lateinit var observer: TestObserver

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    open fun setup() {
        setupViewModel()
        observeViewModel()
    }

    protected abstract fun setupViewModel()

    protected fun assertViewStateError(exception: Exception) {
        observer.viewState?.let {
            assertTrue(it.isError())
            assertEquals(it.output.error, exception)
        }
    }

    protected fun assertViewStateSuccess(value: Any) {
        observer.viewState?.let {
            assertTrue(it.isSuccess())
            assertEquals(it.output.value, value)
        }
    }

    open fun teardown() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    private fun observeViewModel() {
        observer = TestObserver()
        for(channel in viewModel.getChannels()) {
            viewModel.observe(channel, getLifecycleOwner(), observer)
        }
    }

    private fun getLifecycleOwner(): LifecycleOwner {
        val lifecycleOwner: LifecycleOwner = mock()
        val lifecycle = LifecycleRegistry(lifecycleOwner).apply {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }

        whenever(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        return lifecycleOwner
    }
}

class TestObserver: Observer<ViewState> {
    var viewState: ViewState? = null

    override fun onChanged(value: ViewState?) {
        viewState = value
    }
}