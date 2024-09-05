package com.taskyproject.tasky.presentation.eventdetails

import android.net.Uri
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Option
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.model.TimeOption
import com.taskyproject.tasky.domain.model.VisitorOption

sealed interface EventDetailsEvent {
    data class OnEventPhotosSelect(val photos: List<Uri>) : EventDetailsEvent
    data object OnReminderDropdownStateChange : EventDetailsEvent
    data class OnReminderOptionSelect(val option: TimeOption) : EventDetailsEvent
    data class OnVisitorOptionChange(val option: VisitorOption) : EventDetailsEvent
    data object OnEditClick : EventDetailsEvent
    data object OnCloseEditClick : EventDetailsEvent
    data object OnSaveClick : EventDetailsEvent
    data object OnEditTitleClick : EventDetailsEvent
    data class OnTitleChange(val title: String) : EventDetailsEvent
    data class OnDescriptionChange(val description: String) : EventDetailsEvent
    data object OnEditDescriptionClick : EventDetailsEvent
    data class OnStartDateSelect(val millis: Long) : EventDetailsEvent
    data class OnEndDateSelect(val millis: Long) : EventDetailsEvent
    data object OnStartDateClick : EventDetailsEvent
    data object OnEndDateClick : EventDetailsEvent
    data class OnStartTimeSelect(val hours: Int, val minutes: Int) : EventDetailsEvent
    data class OnEndTimeSelect(val hours: Int, val minutes: Int) : EventDetailsEvent
    data object OnStartTimeClick : EventDetailsEvent
    data object OnEndTimeClick : EventDetailsEvent
    data class OnVisitorEmailEnter(val email: String) : EventDetailsEvent
    data object OnAddVisitorClick : EventDetailsEvent
    data object OnVisitorDialogCloseClick : EventDetailsEvent
    data object OnVisitorDialogOpenClick : EventDetailsEvent
    data class OnAttendeeDeleteClick(val attendee: EventAttendee) : EventDetailsEvent
    data class OnDeletePhotoClick(val photo: Uri) : EventDetailsEvent
}