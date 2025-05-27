package it.xpug.kata.birthday_greetings.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XDateTest {
    @Test
    fun `parses date correctly`() {
        val date = XDate.of(1789, 1, 24)

        assertThat(date.month).isEqualTo(1)
        assertThat(date.day).isEqualTo(24)
    }

    @Test
    fun isSameDate() {
        val date = XDate.of(1789, 1, 24)
        val sameDay = XDate.of(2001, 1, 24)
        val notSameDay = XDate.of(1789, 1, 25)
        val notSameMonth = XDate.of(1789, 2, 25)

        assertThat(date.isSameDay(sameDay))
            .`as`("same")
            .isTrue()
        assertThat(date.isSameDay(notSameDay))
            .`as`("not same day")
            .isFalse()
        assertThat(date.isSameDay(notSameMonth))
            .`as`("not same month")
            .isFalse()
    }

}
