package br.com.wirecard.plugin.main

import android.app.Application
import br.com.wirecard.base.business.strategy.DeviceIDStrategy
import br.com.wirecard.base.view.strategy.DateFormatterStrategy
import br.com.wirecard.base.view.strategy.OrderStatusTranslateStrategy
import br.com.wirecard.base.view.strategy.PaymentMethodTranslateStrategy
import br.com.wirecard.feature.login.business.interactor.LoginUseCase
import br.com.wirecard.feature.login.gateway.di.LoginInjector
import br.com.wirecard.feature.order.business.interactor.GetOrderDetailUseCase
import br.com.wirecard.feature.order.business.interactor.GetOrderListUseCase
import br.com.wirecard.feature.order.business.interactor.OrderRepository
import br.com.wirecard.feature.order.gateway.di.OrderInjector
import br.com.wirecard.feature.order.view.di.OrderViewInjector
import br.com.wirecard.feature.splash.business.GetTokenUseCase
import br.com.wirecard.feature.splash.gateway.di.SplashInjector
import br.com.wirecard.plugin.dao.SharedPreferencesDAO
import br.com.wirecard.plugin.repository.AuthRepository
import br.com.wirecard.plugin.repository.AuthRepositoryImpl
import br.com.wirecard.plugin.repository.OrderRepositoryImpl
import br.com.wirecard.plugin.strategy.DateFormatterStrategyImpl
import br.com.wirecard.plugin.strategy.DeviceIDStrategyImpl
import br.com.wirecard.plugin.strategy.Translator

class BaseApplication: Application() {
    private val sessionDAO by lazy { SharedPreferencesDAO(this) }
    private val orderRepository by lazy { createOrderRepository() }
    private val authRepository by lazy { createAuthRepository() }
    private val deviceIDStrategy by lazy{ createDeviceIDStrategy() }

    private val baseAuthUrl = "https://connect-sandbox.moip.com.br/"
    private val baseApiUrl = "https://sandbox.moip.com.br/v2/"

    override fun onCreate() {
        super.onCreate()
        injectFeatureOrder()
        injectFeatureLogin()
        injectFeatureSplash()
    }

    private fun injectFeatureSplash() {
        SplashInjector.injector = object: SplashInjector {
            override val tokenUseCase: GetTokenUseCase
                get() = GetTokenUseCase(authRepository)
        }
    }

    private fun injectFeatureLogin() {
        LoginInjector.injector = object: LoginInjector {
            override val loginUseCase: LoginUseCase
                get() = LoginUseCase(authRepository, deviceIDStrategy)
        }
    }

    private fun injectFeatureOrder() {
        OrderInjector.injector = object: OrderInjector {
            override val orderListUseCase: GetOrderListUseCase
                get() = GetOrderListUseCase(orderRepository)
            override val orderDetailUseCase: GetOrderDetailUseCase
                get() = GetOrderDetailUseCase(orderRepository)
        }

        OrderViewInjector.injector = object: OrderViewInjector {
            override val orderStatusTranslator: OrderStatusTranslateStrategy
                get() = Translator()
            override val paymentMethodTranslator: PaymentMethodTranslateStrategy
                get() = Translator()
            override val dateFormatter: DateFormatterStrategy
                get() = DateFormatterStrategyImpl()
        }
    }

    private fun createDeviceIDStrategy(): DeviceIDStrategy {
        return DeviceIDStrategyImpl(sessionDAO)
    }

    private fun createAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(baseAuthUrl, sessionDAO)
    }

    private fun createOrderRepository(): OrderRepository {
       return OrderRepositoryImpl(baseApiUrl, sessionDAO)
    }
}