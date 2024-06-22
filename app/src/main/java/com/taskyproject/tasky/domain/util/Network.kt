package com.taskyproject.tasky.domain.util

import com.taskyproject.tasky.R
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.net.SocketTimeoutException

private fun handleIoException(e: IOException): Result.Failure {
    return when (e) {
        is SocketTimeoutException -> Result.Failure(errorMessageId = R.string.api_error_timeout)
        else -> Result.Failure(errorMessageId = R.string.api_error_no_connection)
    }
}

fun handleApiError(ex: Exception): Result.Failure {
    return when(ex) {
        is ClientRequestException -> Result.Failure(exception = ex, errorCode = ex.response.status.value, errorMessageId = R.string.api_error_4xx)
        is ServerResponseException -> Result.Failure(exception = ex, errorCode = ex.response.status.value, errorMessageId = R.string.api_error_5xx)
        is IOException -> handleIoException(ex)
        else -> Result.Failure(exception = ex, errorMessageId = R.string.api_error_unknown)
    }
}