package it.xpug.kata.birthday_greetings.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmployeeTest {
    @Test
    fun `should correctly identify employee birthday`() {
        val employeeBirthdate = XDate.of(1990, 1, 31)
        val employee = Employee("foo", "bar", employeeBirthdate, "a@b.c")

        assertThat(employee.isBirthday(XDate.of(1990, 2, 3)))
            .`as`("not his birthday")
            .isFalse()
        assertThat(employee.isBirthday(XDate.of(1990, 1, 31)))
            .`as`("his birthday")
            .isTrue()
    }
}
