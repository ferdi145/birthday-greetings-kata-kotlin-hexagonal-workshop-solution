package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.Greeting
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForSendingGreetings
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.*

class SmtpForSendingGreetings(
    private val smtpHost: String,
    private val smtpPort: Int,
    private val senderEmail: String
) : ForSendingGreetings {
    
    override fun sendBirthdayGreeting(greeting: Greeting) {
        with(greeting) {
            sendMail(recipientMail, subject, text)
        }
    }

    private fun sendMail(to: String, subject: String, body: String) {
        val props = Properties()
        props["mail.smtp.host"] = smtpHost
        props["mail.smtp.port"] = smtpPort.toString()
        props["mail.mime.charset"] = "UTF-8"

        val session = Session.getInstance(props, null)
        val msg: Message = MimeMessage(session)

        msg.setFrom(InternetAddress(senderEmail))
        msg.setRecipient(Message.RecipientType.TO, InternetAddress(to))
        msg.subject = subject
        msg.setText(body)
        msg.setHeader("Content-Type", "text/plain; charset=UTF-8")

        // Simulate slow dependency
        Thread.sleep(2000L)
        Transport.send(msg)
    }
} 