package br.com.wirecard.plugin.repository

import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.plugin.dao.SessionDAO
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

abstract class LoggedInRepositoryTest<T: LoggedInRepository>: AbstractRepositoryTest<T>() {
    protected val authToken = "token"
    protected lateinit var sessionDAO: SessionDAO

    @Before
    override fun setup() {
        setupSessionDAO()
        super.setup()
    }

    private fun setupSessionDAO() {
        sessionDAO = mock()
        val token = mock<Token>()
        whenever(token.accessToken).thenReturn(authToken)
        whenever(sessionDAO.getToken()).thenReturn(token)
    }

    final override fun setupRepository() {
        val httpUrl = setupUrl()
        setupRepository(httpUrl)
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `when auth is required, then authorization header is set`() {
        enqueueEmptyResponse(200)

        repository.dumbRequest()

        val request = server.takeRequest()
        val header = request.getHeader("Authorization")
        assertEquals(header, "OAuth $authToken")
    }

    abstract fun setupRepository(httpUrl: String)

    protected fun enqueueEmptyResponse(code: Int) {
        val response = MockResponse().apply {
            setResponseCode(code)
            setBody("{}")
        }

        server.enqueue(response)
    }
}