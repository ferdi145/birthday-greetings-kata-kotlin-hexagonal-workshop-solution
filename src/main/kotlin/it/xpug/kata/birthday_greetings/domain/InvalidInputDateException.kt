package it.xpug.kata.birthday_greetings.domain

class InvalidInputDateException(dateInput: String) : RuntimeException("Invalid date format: '$dateInput'. Please provide a date in the format YYYY/MM/DD.") 