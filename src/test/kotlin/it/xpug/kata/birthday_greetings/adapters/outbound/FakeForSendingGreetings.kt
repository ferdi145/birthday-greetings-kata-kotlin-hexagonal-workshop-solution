package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.Greeting
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForSendingGreetings

class FakeForSendingGreetings : ForSendingGreetings {
    val received = mutableListOf<Greeting>()
    
    override fun sendBirthdayGreeting(greeting: Greeting) {
        this.received.add(greeting)
    }


}