package it.xpug.kata.birthday_greetings.adapters.outbound

import it.xpug.kata.birthday_greetings.domain.Employee
import it.xpug.kata.birthday_greetings.domain.XDate
import it.xpug.kata.birthday_greetings.domain.ports.outbound.ForGettingEmployees
import java.io.File

class FileForGettingEmployees(private val filePath: String) : ForGettingEmployees {
    private val EXPECTED_HEADER = "last_name, first_name, date_of_birth, email"
    
    override fun findEmployeesForBirthdayAt(xdate: XDate): List<Employee> {
        return findAllEmployees()
            .filter { it.isBirthday(xdate) }
    }
    
    private fun findAllEmployees(): List<Employee> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("File ${file.path} does not exist")
        }

        val lines = try {
            file.readLines()
        } catch (e: Exception) {
            throw IllegalArgumentException("Error reading file: ${e.message}", e)
        }

        require(lines.isNotEmpty()) { "File is empty" }

        val header = lines.first().trim()
        if (header != EXPECTED_HEADER) {
            throw IllegalArgumentException("Invalid file format. Expected header: $EXPECTED_HEADER")
        }

        val employees = lines.asSequence()
            .drop(1)  // Skip header
            .filter { it.isNotBlank() }
            .map { parseLine(it) }
            .toList()

        return removeDuplicateEmployees(employees)
    }

    private fun parseLine(line: String): Employee {
        val fields = line
            .split(",")
            .map { it.trim() }

        if (fields.size != 4) {
            throw IllegalArgumentException("Invalid line format: Expected 4 fields but found ${fields.size}")
        }

        val lastName = fields[0]
        val firstName = fields[1]
        val birthDate = fields[2]
        val email = fields[3]

        if (birthDate.isEmpty()) {
            throw IllegalArgumentException("Birth date is required")
        }

        if (email.isEmpty()) {
            throw IllegalArgumentException("Email is required")
        }

        return Employee(
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
            email = email
        )
    }

    private fun removeDuplicateEmployees(employees: List<Employee>): List<Employee> {
        return employees.distinctBy {
            it.email
        }
    }
} 