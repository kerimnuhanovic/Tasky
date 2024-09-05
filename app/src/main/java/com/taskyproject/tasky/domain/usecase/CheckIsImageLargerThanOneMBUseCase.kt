package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.presentation.util.ONE_MB
import javax.inject.Inject

class CheckIsImageLargerThanOneMBUseCase @Inject constructor(

) {
    operator fun invoke(fileSize: Long): Boolean {
        return fileSize >= ONE_MB
    }
}