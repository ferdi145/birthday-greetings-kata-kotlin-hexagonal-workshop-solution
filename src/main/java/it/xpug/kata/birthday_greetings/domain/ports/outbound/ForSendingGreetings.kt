package it.xpug.kata.birthday_greetings.domain.ports.outbound

import it.xpug.kata.birthday_greetings.domain.Greeting

interface ForSendingGreetings {
    fun sendBirthdayGreeting(greeting: Greeting)
} 