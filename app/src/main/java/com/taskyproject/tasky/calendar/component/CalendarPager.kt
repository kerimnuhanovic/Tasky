package com.taskyproject.tasky.calendar.component

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.taskyproject.tasky.calendar.core.RelativePosition
import java.time.LocalDate

@Composable
internal fun CalendarPager(
    loadedDates: Array<List<LocalDate>>,
    loadNextDates: (date: LocalDate) -> Unit,
    loadPrevDates: (date: LocalDate) -> Unit,
    content: @Composable (currentPage: Int) -> Unit
) {
    val initialized = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = RelativePosition.CURRENT.ordinal, pageCount = {
        RelativePosition.entries.size
    })
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == RelativePosition.NEXT.ordinal) {
            loadNextDates(loadedDates[RelativePosition.CURRENT.ordinal].first())
            pagerState.scrollToPage(RelativePosition.CURRENT.ordinal)
        }
        if (pagerState.currentPage == RelativePosition.PREVIOUS.ordinal && initialized.value) {
            loadPrevDates(loadedDates[RelativePosition.PREVIOUS.ordinal].first())
            pagerState.scrollToPage(RelativePosition.CURRENT.ordinal)
        }
    }
    LaunchedEffect(Unit) {
        initialized.value = true
    }
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { currentPage ->
        content(currentPage)
    }
}