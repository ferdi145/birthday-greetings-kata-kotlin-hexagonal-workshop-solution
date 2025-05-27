package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.XDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.io.path.createTempFile

class FileEmployeeRepositoryTest {

    @Test
    fun `should read birthday employees from file correctly`() {
        // Given
        val tempFile = createTempFile(prefix = "employees_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982-10-08, john.doe@foobar.com
            Ann, Mary, 1982-10-08, mary.ann@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(XDate("1982/10/08"))

        // Then
        assertEquals(2, employees.size)

        val john = employees[0]
        assertEquals("John", john.firstName)
        assertEquals("Doe", john.lastName)
        assertEquals("john.doe@foobar.com", john.email)

        val mary = employees[1]
        assertEquals("Mary", mary.firstName)
        assertEquals("Ann", mary.lastName)
        assertEquals("mary.ann@foobar.com", mary.email)
    }

    @Test
    fun `should not read employees who do not have their birthay`() {
        // Given
        val tempFile = createTempFile(prefix = "employees_", suffix = ".txt").toFile()
        val birthday = "1982-10-08"
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Ann, Mary,  2000-01-01, mary.ann@foobar.com
            Doe, John, $birthday, john.doe@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(XDate("1982/10/08"))

        // Then
        assertEquals(1, employees.size)
        val john = employees[0]
        assertEquals("john.doe@foobar.com", john.email)
    }
    
    @Test
    fun `should handle empty file correctly`() {
        // Given
        val tempFile = createTempFile(prefix = "empty_employees_", suffix = ".txt").toFile()
        tempFile.writeText("last_name, first_name, date_of_birth, email")

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(xDate())

        // Then
        assertTrue(employees.isEmpty())
    }

    @Test
    fun `should throw exception when file does not exist`() {
        // Given
        val nonExistentFile = "non_existent_file.txt"
        val repository = FileEmployeeRepository(nonExistentFile)

        // When & Then: should throw IllegalArgumentException
        assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }
    }

    @Test
    fun `should throw exception when header format is invalid`() {
        // Given
        val tempFile = createTempFile(prefix = "invalid_header_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            wrong, header, format
            Doe, John, 1982/10/08, john.doe@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When & Then: should throw IllegalArgumentException
        assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }
    }

    @Test
    fun `should throw exception when required field is empty`() {
        // Given
        val tempFile = createTempFile(prefix = "missing_field_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe, John, , john.doe@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When & Then: should throw IllegalArgumentException
        assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }
    }

    @Test
    fun `should throw exception when csv line has unequal parts than header`() {
        // Given
        val tempFile = createTempFile(prefix = "missing_field_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe, John, john.doe@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When & Then: should throw IllegalArgumentException
        assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }
            .let { assertThat(it.message).contains("Invalid line format: Expected 4 fields but found 3") }
    }

    @Test
    fun `should trim whitespaces in fields`() {
        // Given
        val tempFile = createTempFile(prefix = "whitespace_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe,    John   ,   1982-10-08   ,   john.doe@foobar.com   
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(XDate("1982/10/08"))

        // Then
        assertEquals(1, employees.size)
        val employee = employees[0]
        assertEquals("John", employee.firstName)
        assertEquals("Doe", employee.lastName)
        assertEquals("john.doe@foobar.com", employee.email)
    }

    @Test
    fun `should skip empty lines`() {
        // Given
        val tempFile = createTempFile(prefix = "empty_lines_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            
            Doe, John, 1982-10-08, john.doe@foobar.com
            
            Ann, Mary, 1982-10-08, mary.ann@foobar.com
            
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(XDate("1982/10/08"))

        // Then
        assertEquals(2, employees.size)
    }

    @Test
    fun `should ignore duplicate employee entries`() {
        // Given
        val tempFile = createTempFile(prefix = "duplicates_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982-10-08, john.doe@foobar.com
            Doe, John, 1982-10-08, john.doe@foobar.com
        """.trimIndent()
        )

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When
        val employees = repository.findEmployeesForBirthdayAt(XDate("1982/10/08"))

        // Then
        assertEquals(1, employees.size, "Should only contain one employee")

        val employee = employees[0]
        assertEquals("John", employee.firstName)
        assertEquals("Doe", employee.lastName)
        assertTrue(employee.isBirthday(XDate("1982/10/08")))
        assertEquals("john.doe@foobar.com", employee.email)
    }

    @Test
    fun `should throw exception when file exists but is not readable`() {
        // Given
        val tempFile = createTempFile(prefix = "unreadable_", suffix = ".txt").toFile()
        tempFile.writeText(
            """
            last_name, first_name, date_of_birth, email
            Doe, John, 1982/10/08, john.doe@foobar.com
        """.trimIndent()
        )
        tempFile.setReadable(false)

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When/Then
        assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }
    }

    @Test
    fun `should throw exception when file is completely empty`() {
        // Given
        val tempFile = createTempFile(prefix = "completely_empty_", suffix = ".txt").toFile()
        tempFile.writeText("")  // Completely empty file without any lines

        val repository = FileEmployeeRepository(tempFile.absolutePath)

        // When & Then: should throw IllegalArgumentException
        val exception = assertThrows<IllegalArgumentException> {
            repository.findEmployeesForBirthdayAt(xDate())
        }

        // Verify the exception message is appropriate for an empty file
        assertEquals("File is empty", exception.message)
    }

    private fun xDate(): XDate = XDate("2025/01/01")
} 