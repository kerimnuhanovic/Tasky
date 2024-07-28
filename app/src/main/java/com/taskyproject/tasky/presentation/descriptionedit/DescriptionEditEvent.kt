package com.taskyproject.tasky.presentation.descriptionedit

sealed interface DescriptionEditEvent {
    data class OnTitleEnter(val title: String) : DescriptionEditEvent
    data object OnSaveClick : DescriptionEditEvent
    data object OnBackClick : DescriptionEditEvent
}