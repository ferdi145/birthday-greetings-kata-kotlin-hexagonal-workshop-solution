package it.xpug.kata.birthday_greetings.domain.ports.outbound

import it.xpug.kata.birthday_greetings.domain.Greeting

class FakeForSendingGreetings : ForSendingGreetings {
    val received = mutableListOf<Greeting>()
    
    override fun sendBirthdayGreeting(greeting: Greeting) {
        this.received.add(greeting)
    }


}