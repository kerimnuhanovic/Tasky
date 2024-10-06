package com.taskyproject.tasky.presentation.eventdetails

import android.net.Uri
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.model.TimeOption
import com.taskyproject.tasky.domain.model.VisitorOption
import java.time.LocalDate
import java.time.LocalTime

data class EventDetailsState(
    val isEditable: Boolean = false,
    val title: String = "Meeting",
    val description: String = "Event Description",
    val eventPhotos: List<Uri> = listOf(),
    val fromTime: LocalTime = LocalTime.of(8,0),
    val toTime: LocalTime = LocalTime.of(8,30),
    val fromDate: LocalDate = LocalDate.now(),
    val toDate: LocalDate = LocalDate.now(),
    val isReminderDropdownExpanded: Boolean = false,
    val reminderOptions: List<TimeOption> = TimeOption.listTimeOptions(),
    val selectedReminderOption: TimeOption = TimeOption.TenMinutesBefore,
    val selectedVisitorOption: VisitorOption = VisitorOption.All,
    val isStartDatePickerOpened: Boolean = false,
    val isEndDatePickerOpened: Boolean = false,
    val isStartTimePickerOpened: Boolean = false,
    val isEndTimePickerOpened: Boolean = false,
    val isVisitorDialogOpened: Boolean = false,
    val visitorEmail: String = "",
    val isVisitorAdditionInProgress: Boolean = false,
    val visitorErrorMessageId: Int? = null,
    val attendees: List<EventAttendee> = emptyList(),
    val isCreateOperation: Boolean = true,
    val initialPhotos: List<Photo> = emptyList(),
    val host: String = "",
    val attendeesToDelete: List<EventAttendee> = emptyList(),
    val photosToDelete: List<Photo> = emptyList(),
    val initialEventAttendees: List<EventAttendee> = emptyList(),
    val isUserEventCreator: Boolean = true,
    val isJoinOrLeaveEventInProgress: Boolean = false,
    val isGoing: Boolean = true
)
