package com.taskyproject.tasky.domain.usecase

import javax.inject.Inject

class CheckIsNameValid @Inject constructor(

) {
    operator fun invoke(name: String): Boolean {
        return name.length in 5..49
    }
}