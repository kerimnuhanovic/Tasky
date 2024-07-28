package com.taskyproject.tasky.domain.model

sealed class VisitorOption(val label: String) {
    data object All : VisitorOption("All")
    data object Going : VisitorOption("Going")
    data object NotGoing : VisitorOption("Not going")
}