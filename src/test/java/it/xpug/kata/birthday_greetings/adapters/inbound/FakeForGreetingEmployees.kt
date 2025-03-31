package it.xpug.kata.birthday_greetings.adapters.inbound

import it.xpug.kata.birthday_greetings.domain.XDate
import it.xpug.kata.birthday_greetings.domain.ports.inbound.ForGreetingEmployees

class FakeForGreetingEmployees : ForGreetingEmployees {
    val receivedDates = mutableListOf<XDate>()

    override fun greetEmployees(date: XDate) {
        receivedDates.add(date)
    }
} 