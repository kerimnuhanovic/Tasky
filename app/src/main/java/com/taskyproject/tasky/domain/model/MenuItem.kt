package com.taskyproject.tasky.domain.model

data class MenuItem(val label: String, val onItemClick: () -> Unit)