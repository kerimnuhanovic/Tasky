package com.taskyproject.tasky.playground

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun main() {
    val time1: Long = System.currentTimeMillis()
    val time2: Long = System.currentTimeMillis() + 100
    val instant = Instant.ofEpochMilli(1725295500000)
    println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
    println(time1 > time2)
}