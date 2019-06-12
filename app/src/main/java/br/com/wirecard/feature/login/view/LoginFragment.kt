package br.com.wirecard.feature.login.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import br.com.wirecard.R
import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.exception.HttpException
import br.com.wirecard.base.view.ui.BaseFragment
import br.com.wirecard.feature.login.gateway.mvvm.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment<LoginViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn.setOnClickListener {onLogin()}
        reveal(loginInputLayout)
        reveal(passwordInputLayout)
        reveal(loginBtn)
    }

    private fun onLogin() {
        viewModel.login(loginTxt.editableText.toString(), passwordTxt.editableText.toString())
    }

    override fun handleSuccess(value: Any?) {
        Log.w("LOGIN", "handle success")

        if(value is Token) {
            Log.w("LOGIN", "token is: "+value.accessToken)
            findNavController().navigate(R.id.action_login_to_orderList)
        }
    }

    override fun handleError(error: Throwable?) {
        Log.e("LOGIN", "Error", error)
    }

    override fun handleHttpError(error: HttpException) {
        Log.e("LOGIN", "HTTP Error: ${error.code} msg: ${error.message}", error)
    }
}