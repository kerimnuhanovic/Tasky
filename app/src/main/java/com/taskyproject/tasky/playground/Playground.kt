package com.taskyproject.tasky.playground

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    val instant = Instant.ofEpochMilli(1718797595010)

    // Step 2: Convert Instant to LocalDateTime using the system default time zone
    val time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    println(time)
}