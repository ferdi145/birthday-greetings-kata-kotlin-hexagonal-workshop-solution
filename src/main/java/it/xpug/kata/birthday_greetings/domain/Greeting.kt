package it.xpug.kata.birthday_greetings.domain

data class Greeting(val recipientMail: String, val subject: String, val text: String) {
    companion object {
        fun of(employee: Employee): Greeting {
            val subject = "Happy Birthday!"
            val body = "Happy Birthday, dear ${employee.firstName}!"
            return Greeting(
                recipientMail = employee.email,
                subject = subject,
                text = body
            )
        }
    }
}


