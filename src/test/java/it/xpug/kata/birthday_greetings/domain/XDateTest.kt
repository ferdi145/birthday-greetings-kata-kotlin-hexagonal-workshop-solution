package it.xpug.kata.birthday_greetings.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class XDateTest {
    @Test
    fun `parses date correctly`() {
        val date = XDate("1789/01/24")

        assertThat(date.month).isEqualTo(1)
        assertThat(date.day).isEqualTo(24)
    }

    @Test
    fun isSameDate() {
        val date = XDate("1789/01/24")
        val sameDay = XDate("2001/01/24")
        val notSameDay = XDate("1789/01/25")
        val notSameMonth = XDate("1789/02/25")

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
