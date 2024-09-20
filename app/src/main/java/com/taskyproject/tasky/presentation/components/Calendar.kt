package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.calendar.ExpandableCalendar
import com.taskyproject.tasky.calendar.core.calendarDefaultTheme
import com.taskyproject.tasky.ui.theme.Black
import com.taskyproject.tasky.ui.theme.CalendarGray
import com.taskyproject.tasky.ui.theme.TaskyTheme
import java.time.LocalDate

@Composable
fun Calendar(
    onDateSelect: (LocalDate) -> Unit
) {
    ExpandableCalendar(
        theme = calendarDefaultTheme.copy(
            dayShape = CircleShape,
            backgroundColor = CalendarGray,
            selectedDayBackgroundColor = Black,
            dayValueTextColor = Black,
            selectedDayValueTextColor = Color.White,
            headerTextColor = Black,
            weekDaysTextColor = Black
        ), onDayClick = {
            onDateSelect(it)
        })
}

@Composable
@Preview
fun CalendarPreview() {
    TaskyTheme {
        Calendar(
            onDateSelect = {}
        )
    }
}