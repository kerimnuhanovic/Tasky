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
    data class OnDeleteAgendaItemClick(val agendaItem: AgendaItem) : AgendaEvent
    data class OnMoreClick(val index: Int) : AgendaEvent
    data object OnConfirmDeleteClick : AgendaEvent
    data object OnDismissModalClick : AgendaEvent
    data object OnLogoutMenuClick : AgendaEvent
    data object OnLogoutClick : AgendaEvent
    data object OnLogoutConfirmClick : AgendaEvent
    data object OnLogoutDismissClick : AgendaEvent
}