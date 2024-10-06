package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.AgendaItem
import com.taskyproject.tasky.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {
    suspend fun listAgenda(date: LocalDate): Flow<List<AgendaItem>>
    suspend fun nuke()
    suspend fun fetchFullAgendaAndStoreToLocalDB(): Result<Unit>
}