package com.taskyproject.tasky.domain.usecase

import javax.inject.Inject

class CheckIsEmailValid @Inject constructor(

) {
    operator fun invoke(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        println("evo me")
        println(emailRegex.matches(email))
        return emailRegex.matches(email)
    }
}