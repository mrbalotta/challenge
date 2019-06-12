package br.com.wirecard.base.gateway.di

import br.com.wirecard.base.business.interactor.ConnectivityStrategy

interface ConnectivityInjector {
    companion object {
        lateinit var injector: ConnectivityInjector
    }

    val connectivityStrategy: ConnectivityStrategy
}