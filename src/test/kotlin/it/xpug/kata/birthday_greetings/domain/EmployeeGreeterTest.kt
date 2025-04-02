package it.xpug.kata.birthday_greetings.domain

import it.xpug.kata.birthday_greetings.domain.ports.outbound.FakeForSendingGreetings
import it.xpug.kata.birthday_greetings.domain.ports.outbound.InMemoryEmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EmployeeGreeterTest {

    private val employeeRepository = InMemoryEmployeeRepository()
    private val emailService = FakeForSendingGreetings()
    private val sut = EmployeeGreeter(employeeRepository, emailService)

    @Test
    fun `sends greetings when it's somebody's birthday`() {
        val birthDate = "1982/10/08"
        
        employeeRepository.save(Employee("John", "Doe", birthDate, "john.doe@foobar.com"))

        sut.greetEmployees(XDate(birthDate))

        assertThat(emailService.received.first())
            .isEqualTo(
                Greeting(
                    recipientMail = "john.doe@foobar.com",
                    subject = "Happy Birthday!",
                    text = "Happy Birthday, dear John!"
                )
            )

    }
    
    @Test
    fun `sends greetings to multiple employees with birthday on same day`() {
        // given
        val sharedBirthDate = "1980/05/15"
        
        employeeRepository.save(Employee("John", "Doe", sharedBirthDate, "john.doe@example.com"))
        employeeRepository.save(Employee("Jane", "Smith", sharedBirthDate, "jane.smith@example.com"))
        
        // when
        sut.greetEmployees(XDate(sharedBirthDate))
        
        // then
        assertThat(emailService.received).hasSize(2)
        assertThat(emailService.received).containsExactlyInAnyOrder(
            Greeting(
                recipientMail = "john.doe@example.com",
                subject = "Happy Birthday!",
                text = "Happy Birthday, dear John!"
            ),
            Greeting(
                recipientMail = "jane.smith@example.com",
                subject = "Happy Birthday!",
                text = "Happy Birthday, dear Jane!"
            )
        )
    }
    
    @Test
    fun `does not send greetings when nobody has birthday`() {
        // given
        val johnBirthday = "1982/10/08"
        employeeRepository.save(Employee("John", "Doe", johnBirthday, "john.doe@foobar.com"))
        val dateWithNoBirthdays = "1982/11/08"
        
        // when
        sut.greetEmployees(XDate(dateWithNoBirthdays))
        
        // then
        assertThat(emailService.received).isEmpty()
    }
}