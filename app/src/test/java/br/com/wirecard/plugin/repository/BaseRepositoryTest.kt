package br.com.wirecard.plugin.repository

import br.com.wirecard.base.business.exception.HttpException
import br.com.wirecard.base.business.exception.InternetConnectionException
import br.com.wirecard.plugin.api.RetrofitAPI
import br.com.wirecard.plugin.api.RetrofitAPIBuilder
import com.nhaarman.mockitokotlin2.spy
import okhttp3.Interceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Before
import org.junit.Test

class BaseRepositoryTest: AbstractRepositoryTest<MockRepository>() {

    @Before
    override fun setup() {
        super.setup()
    }

    override fun setupRepository() {
        repository = spy(MockRepository(setupUrl()))
    }

    @Test(expected = HttpException::class)
    fun `when any request returns 4XX, then throw HttpException`() {
        val response = MockResponse().apply {setResponseCode(400)}
        server.enqueue(response)

        repository.dumbRequest()
    }

    @Test(expected = HttpException::class)
    fun `when any request returns 5XX, then throw HttpException`() {
        val response = MockResponse().apply {setResponseCode(500)}
        server.enqueue(response)

        repository.dumbRequest()
    }

    @Test(expected = InternetConnectionException::class)
    fun `when any request is timed out, then throw InternetConnectionException`() {
        val response = MockResponse().apply {
            socketPolicy = SocketPolicy.NO_RESPONSE
        }

        server.enqueue(response)

        repository.dumbRequest()
    }
}

class MockRepository(baseUrl: String): BaseRepository(baseUrl) {
    override fun getService(interceptors: List<Interceptor>): RetrofitAPI {
        return RetrofitAPIBuilder(baseUrl, interceptors).build()
    }
}