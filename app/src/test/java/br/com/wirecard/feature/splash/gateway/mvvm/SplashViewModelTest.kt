package br.com.wirecard.feature.splash.gateway.mvvm

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.base.business.exception.AuthenticationException
import br.com.wirecard.base.gateway.mvvm.BaseViewModelTest
import br.com.wirecard.feature.splash.business.SplashRepository
import br.com.wirecard.feature.splash.business.GetTokenUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SplashViewModelTest: BaseViewModelTest<SplashViewModel>() {
    private lateinit var repository: SplashRepository

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
    fun `when getSavedToken throws exception, assert callbacks`() {
        val exception = RuntimeException()
        whenever(repository.getSavedToken()).thenThrow(exception)
        doUserIsLoggedInCheck()
        assertViewStateError(exception)
    }

    @Test
    fun `when token is expired, user is not logged in`() {
        val token = mock<Token>()
        val expired = true
        whenever(token.isExpired()).thenReturn(expired)
        whenever(repository.getSavedToken()).thenReturn(token)
        doUserIsLoggedInCheck()
        assertLogin(expired)
    }

    @Test
    fun `when token is not expired, user is logged in`() {
        val token = mock<Token>()
        val expired = false
        whenever(token.isExpired()).thenReturn(expired)
        whenever(repository.getSavedToken()).thenReturn(token)
        doUserIsLoggedInCheck()
        assertLogin(expired)
    }

    private fun assertLogin(expired: Boolean) {
        val output = observer.viewState?.output?.value as Token?
        assertEquals(output?.isExpired(), expired)
    }

    @Test
    fun `when token is empty, then throw AuthenticationException`() {
        val exception = AuthenticationException()
        whenever(repository.getSavedToken()).thenThrow(exception)
        doUserIsLoggedInCheck()
        assertViewStateError(exception)
    }

    private fun doUserIsLoggedInCheck() {
        runBlocking { viewModel.isUserLoggedIn() }
    }

    override fun setupViewModel() {
        val useCase = GetTokenUseCase(repository).apply { testMode = true }
        viewModel = spy(SplashViewModel(useCase))
    }
}