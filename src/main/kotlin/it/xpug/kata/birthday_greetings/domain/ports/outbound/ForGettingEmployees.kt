package it.xpug.kata.birthday_greetings.domain.ports.outbound

import it.xpug.kata.birthday_greetings.domain.Employee
import it.xpug.kata.birthday_greetings.domain.XDate

interface ForGettingEmployees {
    fun findEmployeesForBirthdayAt(xdate: XDate): List<Employee>
} 