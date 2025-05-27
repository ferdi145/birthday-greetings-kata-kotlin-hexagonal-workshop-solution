package it.xpug.kata.birthday_greetings.domain

class Employee(
    val firstName: String,
    val lastName: String,
    val xdate: XDate,
    val email: String
) {
    fun isBirthday(today: XDate): Boolean {
        return today.isSameDay(xdate)
    }

    override fun toString(): String {
        return "Employee $firstName $lastName <$email> born $xdate"
    }
}
