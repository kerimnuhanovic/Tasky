package com.taskyproject.tasky.presentation.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.formatDate(formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")): String {
    return this.format(formatter)
}

fun millisToLocalDate(millis: Long): LocalDate {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate()
}

fun millisToLocalTime(millis: Long): LocalTime {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime()
}

fun millisToLocalDateTime(millis: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(millis)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun differenceInMinutes(time1: Long, time2: Long): Long {
    val differenceInMillis = time1 - time2
    val differenceInMinutes = differenceInMillis / (1000 * 60)
    return differenceInMinutes
}