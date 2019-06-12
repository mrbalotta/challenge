package br.com.wirecard.feature.login.gateway.mvvm

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.strategy.DeviceIDStrategy
import br.com.wirecard.base.gateway.mvvm.BaseViewModelTest
import br.com.wirecard.feature.login.business.interactor.LoginRepository
import br.com.wirecard.feature.login.business.interactor.LoginUseCase
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest: BaseViewModelTest<LoginViewModel>() {
    private lateinit var repository: LoginRepository

    @Before
    override fun setup() {
        repository = mock()
        super.setup()
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `when login succeeds, assert callbacks`() {
        val token = arrangeToken()

        doLogin()

        assertRepositoryCalls("G", "S")
        assertEquals(observer.viewState?.output?.value, token)
    }

    @Test
    fun `when getToken throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.getToken(any())).thenThrow(exception)

        doLogin()

        assertRepositoryCalls("G", "!S")
        assertViewStateError(exception)
    }

    @Test
    fun `when saveToken throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.saveToken(any())).thenThrow(exception)
        arrangeToken()

        doLogin()

        assertRepositoryCalls("G", "S")
        assertViewStateError(exception)
    }

    private fun arrangeToken(): Token {
        val token = mock<Token>()
        whenever(repository.getToken(any())).thenReturn(token)
        return token
    }

    private fun assertViewStateError(exception: RuntimeException) {
        observer.viewState?.let {
            assertTrue(it.isError())
            assertEquals(it.output.error, exception)
        }
    }

    override fun setupViewModel() {
        val deviceIDStrategy = mock<DeviceIDStrategy>()
        val loginUseCase = LoginUseCase(repository, deviceIDStrategy).apply { testMode = true }
        whenever(deviceIDStrategy.deviceId).thenReturn("deviceId")
        viewModel = spy(LoginViewModel(loginUseCase))
    }

    private fun doLogin() {
        runBlocking {
            viewModel.login(username = "username", password = "password")
        }
    }

    private fun assertRepositoryCalls(vararg args: String) {
        val map = mapOf(
            "G"  to {verify(repository, times(1)).getToken(any())},
            "!G" to {verify(repository, times(0)).getToken(any())},
            "S"  to {verify(repository, times(1)).saveToken(any())},
            "!S" to {verify(repository, times(0)).saveToken(any())}
        )

        for(arg in args) map[arg]?.invoke()
    }
}