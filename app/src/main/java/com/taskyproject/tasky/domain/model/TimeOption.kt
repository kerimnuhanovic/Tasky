package com.taskyproject.tasky.domain.model

sealed class TimeOption(val value: String, val label: String) {
    data object TenMinutesBefore : TimeOption("10", "10 minutes before")
    data object ThirtyMinutesBefore : TimeOption("30", "30 minutes before")
    data object OneHourBefore : TimeOption("60", "1 hour before")
    data object OneDayBefore : TimeOption("1440", "1 day before")

    companion object {
        fun listTimeOptions(): List<TimeOption> {
            return listOf(
                TenMinutesBefore,
                ThirtyMinutesBefore,
                OneHourBefore,
                OneDayBefore
            )
        }

        fun fromTime(minutes: String): TimeOption {
            return when (minutes) {
                "10" -> TenMinutesBefore
                "30" -> ThirtyMinutesBefore
                "60" -> OneHourBefore
                "1440" -> OneDayBefore
                else -> throw IllegalArgumentException("Function called with invalid argument")
            }
        }
    }
}