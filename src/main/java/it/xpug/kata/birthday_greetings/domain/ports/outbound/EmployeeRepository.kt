package it.xpug.kata.birthday_greetings.domain.ports.outbound

import it.xpug.kata.birthday_greetings.domain.Employee

interface EmployeeRepository {
    fun findEmployees(): List<Employee>
} 