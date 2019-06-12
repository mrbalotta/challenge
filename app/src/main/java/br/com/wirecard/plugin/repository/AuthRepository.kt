package br.com.wirecard.plugin.repository

import br.com.wirecard.feature.login.business.interactor.LoginRepository
import br.com.wirecard.feature.splash.business.SplashRepository

interface AuthRepository: LoginRepository, SplashRepository