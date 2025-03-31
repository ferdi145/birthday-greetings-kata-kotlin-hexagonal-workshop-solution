package it.xpug.kata.birthday_greetings.adapters.outbound

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetup
import it.xpug.kata.birthday_greetings.domain.Greeting
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SmtpForSendingGreetingsTest {
    private val NONSTANDARD_PORT = 9999
    private val SENDER_EMAIL = "sender@here.com"
    private lateinit var mailServer: GreenMail
    private val smtpForSendingGreetings = SmtpForSendingGreetings("localhost", NONSTANDARD_PORT, SENDER_EMAIL)

    @BeforeEach
    fun setUp() {
        mailServer = GreenMail(ServerSetup(NONSTANDARD_PORT, null, ServerSetup.PROTOCOL_SMTP))
        mailServer.start()
    }

    @AfterEach
    fun tearDown() {
        mailServer.stop()
    }

    @Test
    fun `sends mail`() {
        // given
        val greeting = Greeting(
            recipientMail = "test@example.com",
            subject = "Happy Birthday!",
            text = "Happy Birthday, dear John!"
        )

        // when
        smtpForSendingGreetings.sendBirthdayGreeting(greeting)

        // then
        assertThat(mailServer.receivedMessages.size)
            .`as`("message not sent?")
            .isEqualTo(1)
        val message = mailServer.receivedMessages[0]
        assertThat(GreenMailUtil.getBody(message))
            .isEqualTo("Happy Birthday, dear John!")
        assertThat(message.subject)
            .isEqualTo("Happy Birthday!")
        assertThat(message.allRecipients)
            .hasSize(1)
        assertThat(message.allRecipients[0].toString())
            .isEqualTo("test@example.com")
    }

    @Test
    fun `uses correct sender email address`() {
        // given
        val greeting = Greeting(
            recipientMail = "test@example.com",
            subject = "Test Subject",
            text = "Test Body"
        )

        // when
        smtpForSendingGreetings.sendBirthdayGreeting(greeting)

        // then
        val message = mailServer.receivedMessages[0]
        assertThat(message.from[0].toString())
            .isEqualTo(SENDER_EMAIL)
    }

    @Test
    fun `sends email with correct mime type`() {
        // given
        val greeting = Greeting(
            recipientMail = "test@example.com",
            subject = "Test Subject",
            text = "Test Body"
        )

        // when
        smtpForSendingGreetings.sendBirthdayGreeting(greeting)

        // then
        val message = mailServer.receivedMessages[0]
        assertThat(message.contentType)
            .isEqualTo("text/plain; charset=UTF-8")
    }

    @Test
    fun `sends email with correct character encoding`() {
        // given
        val greeting = Greeting(
            recipientMail = "test@example.com",
            subject = "Test Subject",
            text = "Test Body with special chars: äöüß"
        )

        // when
        smtpForSendingGreetings.sendBirthdayGreeting(greeting)

        // then
        val message = mailServer.receivedMessages[0]
        assertThat(message.contentType)
            .contains("charset=UTF-8")
        assertThat(GreenMailUtil.getBody(message))
            .isEqualTo("Test Body with special chars: =C3=A4=C3=B6=C3=BC=C3=9F")
    }
}