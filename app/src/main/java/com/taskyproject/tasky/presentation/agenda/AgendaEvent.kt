package com.taskyproject.tasky.presentation.agenda

import com.taskyproject.tasky.domain.model.AgendaItem
import com.taskyproject.tasky.presentation.util.AgendaItemType
import java.time.LocalDate

sealed interface AgendaEvent {
    data class OnDateChange(val date: LocalDate) : AgendaEvent
    data object OnAddClick : AgendaEvent
    data object OnBottomSheetDismiss : AgendaEvent
    data class OnAddNewAgendaItemClick(val agendaItemType: AgendaItemType) : AgendaEvent
    data class OnOpenAgendaItemClick(val agenda: AgendaItem) : AgendaEvent
    data class OnEditAgendaItemClick(val agenda: AgendaItem) : AgendaEvent
    data class OnDeleteAgendaItemClick(val agenda: AgendaItem) : AgendaEvent
    data class OnMoreClick(val index: Int) : AgendaEvent
}