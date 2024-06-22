package com.taskyproject.tasky.domain.usecase

import javax.inject.Inject

class CheckIsPasswordValid @Inject constructor(

) {
    operator fun invoke(password: String): Boolean {
        return password.length > 8 && password.any { it.isDigit() } && password.any { it.isLowerCase() } && password.any { it.isUpperCase() }
    }
}