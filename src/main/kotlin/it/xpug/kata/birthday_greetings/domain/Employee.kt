package it.xpug.kata.birthday_greetings.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Employee(
    val firstName: String,
    val lastName: String,
    val xdate: XDate,
    val email: String
) {

    constructor(
        firstName: String,
        lastName: String,
        birthDate: String,
        email: String
    ) : this(firstName, lastName, XDate(LocalDate.parse(birthDate, FORMATTER)), email)

    fun isBirthday(today: XDate): Boolean {
        return today.isSameDay(xdate)
    }

    override fun toString(): String {
        return "Employee $firstName $lastName <$email> born $xdate"
    }

    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    }

}
