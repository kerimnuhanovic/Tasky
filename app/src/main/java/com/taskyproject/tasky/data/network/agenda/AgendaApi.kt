package com.taskyproject.tasky.data.network.agenda

import com.taskyproject.tasky.data.network.dto.AgendaDto

interface AgendaApi {
    suspend fun deleteAgendaItems(
        eventsIds: List<String>,
        tasksIds: List<String>,
        remindersIds: List<String>
    )
    suspend fun getFullAgenda(): AgendaDto
}