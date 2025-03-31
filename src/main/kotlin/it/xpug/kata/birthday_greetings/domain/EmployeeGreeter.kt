package it.xpug.kata.birthday_greetings.domain

import it.xpug.kata.birthday_greetings.domain.ports.inbound.ForGreetingEmployees
import it.xpug.kata.birthday_greetings.domain.ports.outbound.EmployeeRepository
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForSendingGreetings

class EmployeeGreeter(
    private val employeeRepository: EmployeeRepository,
    private val forSendingGreetings: ForSendingGreetings
) : ForGreetingEmployees {
    override fun greetEmployees(xDate: XDate) {
        employeeRepository.findEmployees()
            .filter { it.isBirthday(xDate) }
            .forEach { employee ->
                forSendingGreetings.sendBirthdayGreeting(
                    Greeting.of(employee)
                )
            }
    }
}
