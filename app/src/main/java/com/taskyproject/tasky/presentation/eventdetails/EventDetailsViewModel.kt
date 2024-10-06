package com.taskyproject.tasky.presentation.eventdetails

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.taskyproject.tasky.R
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.model.TimeOption
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.usecase.CheckIsImageLargerThanOneMBUseCase
import com.taskyproject.tasky.domain.usecase.CreateEventUseCase
import com.taskyproject.tasky.domain.usecase.CreateFileFromUriUseCase
import com.taskyproject.tasky.domain.usecase.GetAttendeeUseCase
import com.taskyproject.tasky.domain.usecase.GetEventUseCase
import com.taskyproject.tasky.domain.usecase.JoinOrLeaveEventUseCase
import com.taskyproject.tasky.domain.usecase.UpdateEventUseCase
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.prepareEventRequest
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.ToastMessage
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.presentation.util.differenceInMinutes
import com.taskyproject.tasky.presentation.util.millisToLocalDate
import com.taskyproject.tasky.presentation.util.millisToLocalTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAttendeeUseCase: GetAttendeeUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val preferences: Preferences,
    private val createFileFromUriUseCase: CreateFileFromUriUseCase,
    private val getEventUseCase: GetEventUseCase,
    private val checkIsImageLargerThanOneMBUseCase: CheckIsImageLargerThanOneMBUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val joinOrLeaveEventUseCase: JoinOrLeaveEventUseCase
) : ViewModel() {
    private val eventId = savedStateHandle.toRoute<Route.EventDetails>().eventId
    private val shouldOpenInEditMode =
        savedStateHandle.toRoute<Route.EventDetails>().shouldOpenInEditMode

    private val _state = MutableStateFlow(
        EventDetailsState(
            isEditable = shouldOpenInEditMode
        )
    )
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getEvent()
    }

    fun onEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.OnEventPhotosSelect -> {
                val newPhotos = event.photos.filter { newImage ->
                    !state.value.eventPhotos.contains(newImage)
                }
                _state.value = _state.value.copy(
                    eventPhotos = _state.value.eventPhotos + newPhotos
                )
            }

            EventDetailsEvent.OnReminderDropdownStateChange -> {
                _state.value = state.value.copy(
                    isReminderDropdownExpanded = !state.value.isReminderDropdownExpanded
                )
            }

            is EventDetailsEvent.OnReminderOptionSelect -> {
                _state.value = state.value.copy(
                    selectedReminderOption = event.option,
                    isReminderDropdownExpanded = false
                )
            }

            is EventDetailsEvent.OnVisitorOptionChange -> {
                _state.value = state.value.copy(
                    selectedVisitorOption = event.option
                )
            }

            EventDetailsEvent.OnCloseEditClick -> {
                _state.value = state.value.copy(
                    isEditable = false
                )
            }

            EventDetailsEvent.OnEditClick -> {
                _state.value = state.value.copy(
                    isEditable = true
                )
            }

            EventDetailsEvent.OnSaveClick -> {
                createOrUpdateEvent()
            }

            EventDetailsEvent.OnEditTitleClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.TitleEdit(state.value.title)))
                }
            }

            is EventDetailsEvent.OnTitleChange -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }

            is EventDetailsEvent.OnDescriptionChange -> {
                _state.value = state.value.copy(
                    description = event.description
                )
            }

            EventDetailsEvent.OnEditDescriptionClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.DescriptionEdit(state.value.description)))
                }
            }

            is EventDetailsEvent.OnStartDateSelect -> {
                _state.value = state.value.copy(
                    fromDate = millisToLocalDate(event.millis),
                    isStartDatePickerOpened = false
                )
            }

            is EventDetailsEvent.OnEndDateSelect -> {
                _state.value = state.value.copy(
                    toDate = millisToLocalDate(event.millis),
                    isEndDatePickerOpened = false
                )
            }

            EventDetailsEvent.OnStartDateClick -> {
                _state.value = state.value.copy(
                    isStartDatePickerOpened = true
                )
            }

            EventDetailsEvent.OnEndDateClick -> {
                _state.value = state.value.copy(
                    isEndDatePickerOpened = true
                )
            }

            EventDetailsEvent.OnEndTimeClick -> {
                _state.value = state.value.copy(
                    isEndTimePickerOpened = true
                )
            }

            is EventDetailsEvent.OnEndTimeSelect -> {
                _state.value = state.value.copy(
                    toTime = LocalTime.of(event.hours, event.minutes),
                    isEndTimePickerOpened = false
                )
            }

            EventDetailsEvent.OnStartTimeClick -> {
                _state.value = state.value.copy(
                    isStartTimePickerOpened = true
                )
            }

            is EventDetailsEvent.OnStartTimeSelect -> {
                _state.value = state.value.copy(
                    fromTime = LocalTime.of(event.hours, event.minutes),
                    isStartTimePickerOpened = false
                )
            }

            EventDetailsEvent.OnAddVisitorClick -> {
                getAttendee()
            }

            EventDetailsEvent.OnVisitorDialogCloseClick -> {
                _state.value = state.value.copy(
                    isVisitorDialogOpened = false
                )
            }

            EventDetailsEvent.OnVisitorDialogOpenClick -> {
                _state.value = state.value.copy(
                    isVisitorDialogOpened = true
                )
            }

            is EventDetailsEvent.OnVisitorEmailEnter -> {
                _state.value = state.value.copy(
                    visitorEmail = event.email
                )
            }

            is EventDetailsEvent.OnAttendeeDeleteClick -> {
                val attendeeToDelete = state.value.initialEventAttendees.find {
                    event.attendee == it
                }
                _state.value = state.value.copy(
                    attendeesToDelete = if (attendeeToDelete != null) state.value.attendeesToDelete.plus(
                        event.attendee
                    ) else state.value.attendeesToDelete,
                    attendees = state.value.attendees.minus(event.attendee)
                )
            }

            is EventDetailsEvent.OnDeletePhotoClick -> {
                val photoToDelete = state.value.initialPhotos.find {
                    event.photo == Uri.parse(it.url)
                }
                _state.value = state.value.copy(
                    eventPhotos = state.value.eventPhotos.minus(event.photo),
                    photosToDelete = if (photoToDelete != null) state.value.photosToDelete.plus(
                        photoToDelete
                    ) else state.value.photosToDelete
                )
            }

            EventDetailsEvent.OnJoinOrLeaveEventClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isJoinOrLeaveEventInProgress = true
                    )
                    when (val result = joinOrLeaveEventUseCase(eventId!!, !state.value.isGoing)) {
                        is Result.Success -> {
                            val currentUserId = preferences.readUserId()
                            val attendees = state.value.attendees.map {
                                if (it.userId == currentUserId) {
                                    it.copy(
                                        isGoing = !it.isGoing
                                    )
                                } else {
                                    it
                                }
                            }
                            _state.value = state.value.copy(
                                isJoinOrLeaveEventInProgress = false,
                                isGoing = !state.value.isGoing,
                                attendees = attendees
                            )
                        }

                        is Result.Failure -> {
                            _state.value = state.value.copy(
                                isJoinOrLeaveEventInProgress = false
                            )
                            _uiEvent.send(
                                UiEvent.ShowToast(
                                    ToastMessage.JoinOrLeaveEventFailed(
                                        result.errorMessageId
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createOrUpdateEvent() {
        if (state.value.isCreateOperation) {
            val eventId = UUID.randomUUID().toString()
            viewModelScope.launch {
                val event = prepareEventRequest(
                    id = eventId,
                    title = state.value.title,
                    description = state.value.description,
                    fromTime = state.value.fromTime,
                    fromDate = state.value.fromDate,
                    toTime = state.value.toTime,
                    toDate = state.value.toDate,
                    host = preferences.readUserId()!!,
                    remindBefore = state.value.selectedReminderOption.value,
                    attendees = state.value.attendees,
                    photos = state.value.eventPhotos.map { photo ->
                        Photo(
                            key = "",
                            url = photo.toString(),
                            eventId = eventId
                        )
                    }
                )
                println(event.remindAt)
                val allPhotos = state.value.eventPhotos.map { photo ->
                    createFileFromUriUseCase(photo)
                }
                val photos = allPhotos.filter {
                    !checkIsImageLargerThanOneMBUseCase(it.length())
                }
                when (val result = createEventUseCase(event, photos)) {
                    is Result.Success -> {
                        val messageId =
                            if (photos.size == allPhotos.size) R.string.event_created else R.string.event_created_with_images_skipped
                        _state.value = state.value.copy(
                            eventPhotos = event.photos.map { photo ->
                                Uri.parse(photo.url)
                            }
                        )
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.EventCreated(messageId)))
                        _uiEvent.send(UiEvent.Navigate(Route.Agenda))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.EventCreationFailed(result.errorMessageId)))
                    }
                }
            }
        } else {
            viewModelScope.launch {
                val event = prepareEventRequest(
                    id = eventId!!,
                    title = state.value.title,
                    description = state.value.description,
                    fromTime = state.value.fromTime,
                    fromDate = state.value.fromDate,
                    toTime = state.value.toTime,
                    toDate = state.value.toDate,
                    host = preferences.readUserId()!!,
                    remindBefore = state.value.selectedReminderOption.value,
                    attendees = state.value.attendees,
                    photos = state.value.eventPhotos.map { photo ->
                        Photo(
                            key = "",
                            url = photo.toString(),
                            eventId = eventId
                        )
                    }
                )
                val allPhotos = state.value.eventPhotos.map { photo ->
                    createFileFromUriUseCase(photo)
                }
                val photos = allPhotos.filter {
                    !checkIsImageLargerThanOneMBUseCase(it.length())
                }
                when (val result = updateEventUseCase(
                    event = event,
                    photos = photos,
                    deletedAttendees = state.value.attendeesToDelete,
                    deletedPhotos = state.value.photosToDelete
                )) {
                    is Result.Success -> {
                        val messageId =
                            if (photos.size == allPhotos.size) R.string.event_updated else R.string.event_updated_with_images_skipped
                        _state.value = state.value.copy(
                            eventPhotos = event.photos.map { photo ->
                                Uri.parse(photo.url)
                            },
                            photosToDelete = emptyList(),
                            attendeesToDelete = emptyList(),
                            isEditable = false
                        )
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.EventUpdated(messageId)))
                    }

                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.EventUpdateFailed(result.errorMessageId)))
                    }
                }
            }
        }
    }

    private fun getAttendee() {
        _state.value = state.value.copy(
            isVisitorAdditionInProgress = true
        )
        viewModelScope.launch {
            when (val result = getAttendeeUseCase(state.value.visitorEmail)) {
                is Result.Success -> {
                    val eventAttendee = EventAttendee(
                        email = result.data.email,
                        userId = result.data.userId,
                        fullName = result.data.fullName,
                        eventId = eventId ?: "",
                        createdAt = LocalDateTime.now(),
                        isGoing = true,
                        remindAt = null
                    )
                    _state.value = state.value.copy(
                        isVisitorAdditionInProgress = false,
                        visitorErrorMessageId = null,
                        isVisitorDialogOpened = false,
                        visitorEmail = "",
                        attendees = state.value.attendees + eventAttendee
                    )
                }

                is Result.Failure -> {
                    _state.value = state.value.copy(
                        isVisitorAdditionInProgress = false,
                        visitorErrorMessageId = result.errorMessageId
                    )
                }
            }
        }
    }

    private fun getEvent() {
        viewModelScope.launch {
            eventId?.let {
                val currentUserId = preferences.readUserId()
                val event = getEventUseCase(it)
                _state.value = state.value.copy(
                    isCreateOperation = false,
                    title = event.title,
                    description = event.description,
                    fromTime = millisToLocalTime(event.from),
                    fromDate = millisToLocalDate(event.from),
                    toTime = millisToLocalTime(event.to),
                    toDate = millisToLocalDate(event.to),
                    selectedReminderOption = TimeOption.fromTime(
                        differenceInMinutes(
                            event.from,
                            event.remindAt
                        ).toString()
                    ),
                    attendees = event.attendees,
                    initialEventAttendees = event.attendees,
                    eventPhotos = event.photos.map { photo ->
                        Uri.parse(photo.url)
                    },
                    initialPhotos = event.photos,
                    host = event.host,
                    isUserEventCreator = event.host == currentUserId,
                    isGoing = event.attendees.find { attendee -> attendee.userId == currentUserId }?.isGoing
                        ?: true
                )
            }
        }
    }
}