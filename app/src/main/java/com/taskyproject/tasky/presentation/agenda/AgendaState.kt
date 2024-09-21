package com.taskyproject.tasky.presentation.agenda

import com.taskyproject.tasky.domain.model.AgendaItem
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.model.Task
import java.time.LocalDate

data class AgendaState(
    val agendaItems: List<AgendaItem> = emptyList(),
    val isBottomSheetOpened: Boolean = false,
    val indexOfOpenedMenu: Int? = null,
    val isConfirmationModalOpen: Boolean = false,
    val agendaItemToDelete: AgendaItem? = null
)
