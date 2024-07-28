package com.taskyproject.tasky.presentation.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.formatDate(formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")): String {
    return this.format(formatter)
}

fun millisToLocalDate(millis: Long): LocalDate {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate()
}