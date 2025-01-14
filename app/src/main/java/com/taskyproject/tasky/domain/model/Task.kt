package com.taskyproject.tasky.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
) : AgendaItem() {
    override fun getStartTime(): Long = time
}
