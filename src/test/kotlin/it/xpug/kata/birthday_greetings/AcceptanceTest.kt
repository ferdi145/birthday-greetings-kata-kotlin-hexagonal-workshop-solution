package it.xpug.kata.birthday_greetings

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetup
import it.xpug.kata.birthday_greetings.adapters.outbound.FileForGettingEmployees
import it.xpug.kata.birthday_greetings.adapters.outbound.SmtpForSendingGreetings
import it.xpug.kata.birthday_greetings.domain.EmployeeGreeter
import it.xpug.kata.birthday_greetings.domain.XDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AcceptanceTest {
    private val NONSTANDARD_PORT = 9999
    private lateinit var mailServer: GreenMail
    private val employeeRepository = FileForGettingEmployees("employee_data.txt")
    private val emailService = SmtpForSendingGreetings("localhost", NONSTANDARD_PORT, "sender@here.com")
    private val employeeGreeter = EmployeeGreeter(employeeRepository, emailService)

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
    fun willSendGreetings_whenItsSomebodysBirthday() {
        employeeGreeter.greetEmployees(XDate("2008/10/08"))

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
            .isEqualTo("john.doe@foobar.com")
    }

}
