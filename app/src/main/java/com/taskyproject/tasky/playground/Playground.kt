package com.taskyproject.tasky.playground

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun main() {
    val instant = Instant.ofEpochMilli(1727479800000)
    println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
}