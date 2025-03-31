package it.xpug.kata.birthday_greetings.adapters.inbound

import it.xpug.kata.birthday_greetings.domain.XDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CliAdapterTest {
    private val fakeGreeter = FakeForGreetingEmployees()
    private val sut = CliAdapter(fakeGreeter)

    @Test
    fun `passes date argument to greeter`() {
        // given
        val dateString = "2025/03/30"

        // when
        sut.invoke(arrayOf(dateString))

        // then
        assertThat(fakeGreeter.receivedDates).hasSize(1)
        assertThat(fakeGreeter.receivedDates.first()).isEqualTo(XDate(dateString))
    }

    @Test
    fun `passes correct date when multiple arguments are provided`() {
        // given
        val dateString = "2025/03/30"

        // when
        sut.invoke(arrayOf(dateString, "extraArg1", "extraArg2"))

        // then
        assertThat(fakeGreeter.receivedDates).hasSize(1)
        assertThat(fakeGreeter.receivedDates.first()).isEqualTo(XDate(dateString))
    }

    @Test
    fun `throws exception when no arguments are provided`() {
        // given
        val emptyArgs = arrayOf<String>()

        // when & then
        val exception = assertThrows(NoDateGivenException::class.java) {
            sut.invoke(emptyArgs)
        }
        assertEquals("No date provided. Please provide a date in the format YYYY/MM/DD.", exception.message)
    }

    @Test
    fun `throws exception when invalid date format is provided`() {
        // given
        val invalidDateFormat = "30-03-2025" // Should be YYYY/MM/DD
        
        // when & then
        val exception = assertThrows(InvalidInputDateException::class.java) {
            sut.invoke(arrayOf(invalidDateFormat))
        }
        assertEquals("Invalid date format: '30-03-2025'. Please provide a date in the format YYYY/MM/DD.", exception.message)
    }
} 