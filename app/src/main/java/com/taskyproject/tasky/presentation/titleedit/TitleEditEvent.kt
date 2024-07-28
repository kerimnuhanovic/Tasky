package com.taskyproject.tasky.presentation.titleedit

sealed interface TitleEditEvent {
    data class OnTitleEnter(val title: String) : TitleEditEvent
    data object OnSaveClick : TitleEditEvent
    data object OnBackClick : TitleEditEvent
}