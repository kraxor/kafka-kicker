package ch.sonofabit.kafka.kicker.lib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloTest {
    @Test
    fun barTest() {
        val hello = Hello()
        assertEquals("bar", hello.foo)
    }
}
