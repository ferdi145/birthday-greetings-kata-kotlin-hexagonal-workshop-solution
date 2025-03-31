package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.Employee
import it.xpug.kata.birthday_greetings.domain.ports.outbound.EmployeeRepository

class InMemoryEmployeeRepository : EmployeeRepository {

    private val employees = mutableListOf<Employee>()

    fun save(employee: Employee) {
        this.employees.add(employee)
    }

    override fun findEmployees(): List<Employee> {
        return employees
    }

}