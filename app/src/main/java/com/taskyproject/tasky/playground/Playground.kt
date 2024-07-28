package com.taskyproject.tasky.playground

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun main() {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
    val end = LocalDateTime.parse("2024-07-27T18:07:06.045183322Z", inputFormatter)
    println(end)
}