package it.xpug.kata.birthday_greetings.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreetingTest {

    @Test
    fun `create greeting for employee`() {
        val birthDate = XDate.of(1990, 1, 31)
        val employee = Employee("foo", "bar", birthDate, "a@b.c")

        val greeting = Greeting.of(employee)

        assertThat(greeting.recipientMail)
            .`as`("mail address")
            .isEqualTo("a@b.c")
        assertThat(greeting.subject)
            .`as`("subject")
            .isEqualTo("Happy Birthday!")
        assertThat(greeting.text)
            .`as`("body")
            .isEqualTo("Happy Birthday, dear foo!")
    }
}