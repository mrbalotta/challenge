package br.com.wirecard.feature.splash.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import br.com.wirecard.R
import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.exception.AuthenticationException
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.splash.gateway.mvvm.SplashViewModel
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment: BaseFragment<SplashViewModel>() {
    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({viewModel.isUserLoggedIn()}, 2000)
    }

    override fun handleSuccess(value: Any?) {
        if(value is Token) {
            if(value.isExpired()) {
                navigateToLogin()
            } else {
                navigateToOrderList()
            }
        }
       // navigateToLogin()

    }

    override fun handleError(error: Throwable?) {
        if(error is AuthenticationException) navigateToLogin()
    }

    private fun navigateToOrderList() {
        findNavController().navigate(R.id.action_splash_to_orderList)
    }

    private fun navigateToLogin() {
        val extras = FragmentNavigatorExtras(logo to "logo")
        findNavController().navigate(R.id.action_splash_to_login, null, null, extras)
    }
}