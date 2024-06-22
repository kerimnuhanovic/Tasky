package com.taskyproject.tasky.domain.util

sealed class Result<out R> {

    data class Success<out T>(val data: T): Result<T>()

    data class Failure(val exception: Exception? = null, val errorCode: Int = 0, val errorMessageId: Int): Result<Nothing>()
}