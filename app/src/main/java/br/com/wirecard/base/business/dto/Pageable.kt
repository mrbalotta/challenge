package br.com.wirecard.base.business.dto

data class Pageable<V>(val page: Int = 0, val wrappedValue: V, val hasMoreItems: Boolean = true)