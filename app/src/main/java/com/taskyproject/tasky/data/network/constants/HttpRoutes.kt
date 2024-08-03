package com.taskyproject.tasky.data.network.constants

object HttpRoutes {
    private const val BASE_URL = "https://tasky.pl-coding.com"
    const val LOGIN = "${BASE_URL}/login"
    const val REGISTER = "${BASE_URL}/register"
    const val ATTENDEE = "${BASE_URL}/attendee"
    const val EVENT = "${BASE_URL}/event"
    const val TASK = "${BASE_URL}/task"
    const val REMINDER = "${BASE_URL}/reminder"
}