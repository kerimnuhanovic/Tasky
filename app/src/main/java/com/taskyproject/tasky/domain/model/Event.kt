package com.taskyproject.tasky.domain.model

data class Event(
    val id: String,
    val isUserEventCreator: Boolean,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val host: String,
    val remindAt: Long,
    val photos: List<Photo>,
    val attendees: List<EventAttendee>
) : AgendaItem() {
    override fun getStartTime(): Long = from
}
