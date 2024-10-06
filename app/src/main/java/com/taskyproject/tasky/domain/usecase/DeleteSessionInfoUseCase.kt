package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.preferences.Preferences
import javax.inject.Inject

class DeleteSessionInfoUseCase @Inject constructor(
    private val preferences: Preferences
) {
    operator fun invoke() {
        preferences.deleteSessionInfo()
    }
}