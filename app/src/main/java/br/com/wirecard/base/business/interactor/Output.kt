package br.com.wirecard.base.business.interactor

import java.util.concurrent.TimeUnit

abstract class Output<V>(val value: V?=null, val error: Throwable? = null) {
    open fun isError(): Boolean = error != null
    open fun isSuccess(): Boolean = !isError()
    fun isEmpty(): Boolean = value != null
}

open class ValueOutput<V>(value: V?=null): Output<V>(value, null) {
    override fun isError(): Boolean = false
}

class ErrorOutput<V>(error: Throwable?, value: V?=null): Output<V>(value, error) {
    override fun isError(): Boolean = true
}

class CachedOutput<V>(
    val cachedTime: Long,
    val timeUnit: TimeUnit,
    value: V?): ValueOutput<V>(value)