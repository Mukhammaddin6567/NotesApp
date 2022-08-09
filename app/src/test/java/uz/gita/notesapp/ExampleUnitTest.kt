package uz.gita.notesapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

    }

    @Test
    fun addition_isWrong() {
        assertEquals(5, 2 + 2)
    }

    @Test
    fun isPhoneCorrect() {
        assertTrue(Check().isPhone("+998936576567"))
    }
}