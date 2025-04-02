package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.Employee
import it.xpug.kata.birthday_greetings.domain.XDate
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForGettingEmployees

class InMemoryForGettingEmployees : ForGettingEmployees {

    private val employees = mutableListOf<Employee>()

    fun save(employee: Employee) {
        this.employees.add(employee)
    }
    
    override fun findEmployeesForBirthdayAt(xdate: XDate): List<Employee> {
        return employees.filter { it.isBirthday(xdate) }
    }

}