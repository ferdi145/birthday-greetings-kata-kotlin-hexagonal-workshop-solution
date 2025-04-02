package it.xpug.kata.birthday_greetings

import it.xpug.kata.birthday_greetings.adapters.inbound.CliAdapter
import it.xpug.kata.birthday_greetings.adapters.outbound.FileEmployeeRepository
import it.xpug.kata.birthday_greetings.adapters.outbound.SmtpForSendingGreetings
import it.xpug.kata.birthday_greetings.domain.EmployeeGreeter

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val cliAdapter = configure()

        cliAdapter.invoke(args)
    }

    private fun configure(): CliAdapter {
        val employeeRepository = FileEmployeeRepository("employee_data.txt")
        val emailService = SmtpForSendingGreetings("localhost", 25, "sender@here.com")
        val employeeGreeter = EmployeeGreeter(employeeRepository, emailService)

        val cliAdapter = CliAdapter(employeeGreeter)
        return cliAdapter
    }
}
