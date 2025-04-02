package it.xpug.kata.birthday_greetings.domain.ports.inbound

import it.xpug.kata.birthday_greetings.domain.XDate

class FakeForGreetingEmployees : ForGreetingEmployees {
    val receivedDates = mutableListOf<XDate>()

    override fun greetEmployees(xDate: XDate) {
        receivedDates.add(xDate)
    }
} 