package it.xpug.kata.birthday_greetings.domain

import java.time.LocalDate

data class XDate(private val date: LocalDate) {

    val day: Int
        get() = date.dayOfMonth

    val month: Int
        get() = date.monthValue

    fun isSameDay(anotherDate: XDate): Boolean {
        return anotherDate.day == this.day && anotherDate.month == this.month
    }

    companion object {
        fun of(year: Int, month: Int, day: Int): XDate = XDate(LocalDate.of(year, month, day))
    }

    override fun toString(): String {
        return date.toString()
    }
}
