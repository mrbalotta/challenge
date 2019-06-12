package br.com.wirecard.base.business.interactor

import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class UseCaseTest {
    private val param = "param"
    private val result = "result"
    private val output: Output<String?> = ValueOutput(result)
    private lateinit var useCase: MockUseCase
    private lateinit var callback: Callback<String?>

    @Before
    fun setup() {
        callback = spy(Callback())
        useCase = spy(MockUseCase(output))
    }

    @Test
    fun `when denied by guard, then call onGuardError function`() {
        whenever(useCase.guard(eq(param))).thenReturn(false)

        useCase.start(param){callback.run(it)}

        assertCallbacks("!EX", "!SU", "GE", "!CB")
    }

    @Test
    fun `when allowed by guard, then call onSuccess function`() {
        useCase.start(param){callback.run(it)}

        assertCallbacks("EX", "SU", "!ER", "CB")
        assertEquals(callback.result.value, result)
    }

    @Test
    fun `when guard throws error, then call onError function`() {
        val exception = RuntimeException()
        whenever(useCase.guard(eq(param))).thenThrow(exception)

        useCase.start(param){callback.run(it)}

        assertCallbacks("!EX", "!SU", "CBE")
        assertErrorCallback(exception)
        assertEquals(callback.result.error, exception)
    }

    @Test
    fun `when execute throws error, then call onError function`() {
        val exception = RuntimeException()
        whenever(useCase.execute(eq(param))).thenThrow(exception)

        useCase.start(param){callback.run(it)}

        assertCallbacks("EX", "!SU", "CBE")
        assertErrorCallback(exception)
        assertEquals(callback.result.error, exception)
    }

    private fun assertCallbacks(vararg args: String) {
        val map = mapOf(
            "EX"  to {verify(useCase, times(1)).execute(eq(param))},
            "!EX" to {verify(useCase, times(0)).execute(eq(param))},
            "SU"  to {verify(useCase, times(1)).onSuccess(eq(output))},
            "!SU" to {verify(useCase, times(0)).onSuccess(any())},
            "ER"  to {verify(useCase, times(1)).onError(any())},
            "!ER" to {verify(useCase, times(0)).onError(any())},
            "CB"  to {verify(callback,times(1)).run(eq(output))},
            "CBE" to {verify(callback,times(1)).run(argThat { this is ErrorOutput } )},
            "!CB" to {verify(callback,times(0)).run(any())},
            "GE"  to {verify(useCase, times(1)).onGuardError()},
            "!GE" to {verify(useCase, times(0)).onGuardError()}
        )

        for(arg in args) map[arg]?.invoke()
    }

    private fun assertErrorCallback(exception: Exception) {
        verify(useCase, times(1)).onError(eq(exception))
    }
}

class Callback<R> {
    lateinit var result: Output<R>

    fun run(value: Output<R>) {
        this.result = value
    }
}

class MockUseCase(val result: Output<String?>): UseCase<String?, String?>() {
    override fun execute(param: String?): Output<String?> {
        return result
    }
}