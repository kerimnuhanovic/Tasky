package com.taskyproject.tasky.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskyproject.tasky.calendar.core.CalendarTheme
import com.taskyproject.tasky.ui.theme.LocalDimensions
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle

/**
 * View that represent one day in the calendar
 * @param date date that view should represent
 * @param weekDayLabel flag that indicates if name of week day should be visible above day value
 * @param modifier view modifier
 */
@Composable
fun DayView(
    date: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    theme: CalendarTheme,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    weekDayLabel: Boolean = true
) {
    val dimensions = LocalDimensions.current
    val isCurrentDay = date == LocalDate.now()
    val dayValueModifier =
        if (isCurrentDay) modifier.background(
            if (isSelected) theme.selectedDayBackgroundColor else theme.dayBackgroundColor,
            shape = theme.dayShape
        )
        else if (isSelected) modifier.background(
            theme.selectedDayBackgroundColor,
            shape = theme.dayShape
        )
        else modifier.background(theme.dayBackgroundColor, shape = theme.dayShape)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .heightIn(max = if (weekDayLabel) 50.dp + 40.dp else 50.dp)
            .widthIn(max = 50.dp)
            .testTag("day_view_column")
    ) {
        Box(
            dayValueModifier
                .padding(5.dp)
                .aspectRatio(1f)
                .clickable { onDayClick(date) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                date.dayOfMonth.toString(),
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = if (isSelected) theme.selectedDayValueTextColor else theme.dayValueTextColor
            )
        }
        Spacer(modifier = Modifier.padding(vertical = dimensions.size4))
        if (weekDayLabel) {
            Text(
                DayOfWeek.entries[date.dayOfWeek.value - 1].getDisplayName(
                    TextStyle.SHORT,
                    LocalContext.current.resources.configuration.locales[0]
                ),
                fontSize = 10.sp,
                color = theme.weekDaysTextColor
            )
            if (isSelected) {
                Spacer(modifier = Modifier.padding(vertical = dimensions.size4))
                Divider(thickness = dimensions.size2, color = theme.selectedDayBackgroundColor)
            }
        }
    }
}