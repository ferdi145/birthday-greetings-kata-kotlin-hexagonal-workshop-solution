package it.xpug.kata.birthday_greetings.adapters.inbound

class NoDateGivenException : RuntimeException("No date provided. Please provide a date in the format YYYY/MM/DD.") 