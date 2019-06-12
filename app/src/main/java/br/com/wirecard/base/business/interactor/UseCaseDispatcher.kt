package br.com.wirecard.base.business.interactor

/**
 * @author Alessandro Balotta de Oliveira
 *
 * An UseCaseDispatcher can run AbstractUseCase on coroutines
 * Interactor's methods are executed on the `executeOn` dispatcher
 * AbstractUseCase's outputting methods are executed on the `resultOn` dispatcher
 */

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import kotlinx.coroutines.*

open class UseCaseDispatcher<in P,R>(
    private val useCase: AbstractUseCase<P, R>,
    private val executeOn: CoroutineDispatcher = Dispatchers.IO,
    private val resultOn: CoroutineDispatcher = Dispatchers.Main
): Interactor<P, R> by useCase {

    fun dispatch(param: P? = null, callback: (R)->Unit): Job? {
        useCase.callback = callback
        onBeforeDispatch()
        return GlobalScope.launch(executeOn) {
            onDispatch(param)
        }
    }

    private suspend fun onDispatch(param: P? = null) {
        try {
            onBeforeExecute()
            onExecute(param)
        } catch(error: Throwable) {
            onError(error)
        }
    }

    private suspend fun onExecute(param: P? = null) {
        if (useCase.guard(param)) {
            onSuccess(useCase.execute(param))
        } else {
            useCase.onGuardError()
        }
    }

    private suspend fun onSuccess(output: R) {
        withContext(resultOn) {
            try {
                onBeforeSuccess()
                useCase.onSuccess(output)
                onFinish()
            } catch(error: Throwable) {
                onError(error)
            }
        }
    }

    private suspend fun onError(error: Throwable) {
        withContext(resultOn) {
            try {
                onBeforeError()
                useCase.onError(error)
                onFinish()
            } catch (e: Throwable) {
                onErrorHalt(e)
            }
        }
    }

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onBeforeDispatch() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onBeforeExecute() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onBeforeSuccess() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onBeforeError() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onFinish() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onErrorHalt(error: Throwable) {}
}