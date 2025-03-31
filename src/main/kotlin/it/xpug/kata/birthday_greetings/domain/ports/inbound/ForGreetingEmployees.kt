package it.xpug.kata.birthday_greetings.domain.ports.inbound

import it.xpug.kata.birthday_greetings.domain.XDate

interface ForGreetingEmployees {
    fun greetEmployees(xDate: XDate)
}