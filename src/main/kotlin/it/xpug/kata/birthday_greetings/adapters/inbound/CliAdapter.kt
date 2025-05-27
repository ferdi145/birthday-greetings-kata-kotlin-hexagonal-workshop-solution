package it.xpug.kata.birthday_greetings.adapters.inbound

import it.xpug.kata.birthday_greetings.domain.InvalidInputDateException
import it.xpug.kata.birthday_greetings.domain.XDate
import it.xpug.kata.birthday_greetings.domain.ports.inbound.ForGreetingEmployees
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CliAdapter(private val forGreetingEmployees: ForGreetingEmployees) {
    fun invoke(args: Array<String>) {
        if (args.isEmpty()) {
            throw NoDateGivenException()
        }

        val date = args[0]

        val xDate = try {
            XDate(LocalDate.parse(date, FORMATTER))
        } catch (e: DateTimeParseException) {
            throw InvalidInputDateException(date)
        }
        
        forGreetingEmployees.greetEmployees(xDate)
    }

    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    }

}
