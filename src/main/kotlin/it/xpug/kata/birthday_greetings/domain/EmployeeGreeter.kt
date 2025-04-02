package it.xpug.kata.birthday_greetings.domain

import it.xpug.kata.birthday_greetings.domain.ports.inbound.ForGreetingEmployees
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForGettingEmployees
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForSendingGreetings

class EmployeeGreeter(
    private val forGettingEmployees: ForGettingEmployees,
    private val forSendingGreetings: ForSendingGreetings
) : ForGreetingEmployees {
    override fun greetEmployees(xDate: XDate) {
        forGettingEmployees.findEmployeesForBirthdayAt(xDate)
            .forEach { employee ->
                forSendingGreetings.sendBirthdayGreeting(
                    Greeting.of(employee)
                )
            }
    }
}
