package it.xpug.kata.birthday_greetings.adapters.inbound

import it.xpug.kata.birthday_greetings.domain.XDate
import it.xpug.kata.birthday_greetings.domain.ports.inbound.ForGreetingEmployees

class CliAdapter(private val forGreetingEmployees: ForGreetingEmployees) {
    fun invoke(args: Array<String>) {
        if (args.isEmpty()) {
            throw NoDateGivenException()
        }
        
        val date = args[0]
        val xDate = XDate(date)
        forGreetingEmployees.greetEmployees(xDate)
    }
}