package br.com.wirecard.base.business.interactor

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.annotation.VisibleForTesting.PROTECTED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * @author Alessandro Balotta de Oliveira
 *
 * This file has the main components of the Business Layer: AbstractUseCase and UseCase
 *
 * An Use Case is a guarded executable unit that receives a nullable param and
 * outputs a non-null results or Unit/Nothing to a callback lambda.
 * Use Cases may be executed on a separated Thread but they don't enforce it.
 * They automatically catch any exceptions and may wrap them in the output.
 *
 * Use Cases implement the Template Method Pattern
 */

abstract class AbstractUseCase<in P, R>: Interactor<P, R> {
    lateinit var callback: (R)->Unit

    fun start(param: P? = null, callback: (R)->Unit) {
        try {
            this.callback = callback
            process(param)
        } catch(error: Throwable) {
            onError(error)
        }
    }

    protected open fun process(param: P?) {
        if(guard(param)) {
            execute(param).also { onSuccess(it!!) }
        } else {
            onGuardError()
        }
    }

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onSuccess(output: R) {
        if(::callback.isInitialized) callback(output)
    }

    @VisibleForTesting(otherwise = PROTECTED)
    internal open fun onGuardError() {}

    @VisibleForTesting(otherwise = PROTECTED)
    internal abstract fun onError(error: Throwable)
}


abstract class UseCase<in P, R>: AbstractUseCase<P, Output<R>>() {
    @VisibleForTesting(otherwise = PRIVATE)
    var testMode: Boolean = false

    override fun onError(error: Throwable) {
        callback(ErrorOutput(error))
    }

    fun dispatch(param: P? = null, callback: (Output<R>)->Unit): Job? {
        val invoker = when {
            testMode -> UseCaseDispatcher(executeOn = Dispatchers.Unconfined, resultOn = Dispatchers.Unconfined, useCase = this)
            else -> UseCaseDispatcher(this)
        }
        return invoker.dispatch(param, callback)
    }
}