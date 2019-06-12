package br.com.wirecard.base.business.filter

import br.com.wirecard.base.business.delimiter.Delimiter

abstract class Filter<T: Delimiter>(val name: String) {
    private lateinit var pair: Pair<String, Delimiter?>

    fun get(): Pair<String, Delimiter?> = pair

    fun set(delimiter: T?) {
        pair = Pair(name, delimiter)
    }
}