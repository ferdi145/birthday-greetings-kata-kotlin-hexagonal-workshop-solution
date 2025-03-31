package it.xpug.kata.birthday_greetings.domain.ports.outbound

import it.xpug.kata.birthday_greetings.domain.Employee

class InMemoryEmployeeRepository : EmployeeRepository {

    private val employees = mutableListOf<Employee>()

    fun save(employee: Employee) {
        this.employees.add(employee)
    }

    override fun findEmployees(): List<Employee> {
        return employees
    }

}