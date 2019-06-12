package br.com.wirecard.base.util

import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

class MaskTest {

    @Test
    fun testMask() {
        val match = Pattern.matches("[0-9,.]+", "5556655")
        Assert.assertTrue(match)
    }
}