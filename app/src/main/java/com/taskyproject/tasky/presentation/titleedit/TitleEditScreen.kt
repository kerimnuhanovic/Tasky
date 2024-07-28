package com.taskyproject.tasky.presentation.titleedit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskyproject.tasky.R
import com.taskyproject.tasky.presentation.components.TextArea
import com.taskyproject.tasky.presentation.util.TITLE_KEY
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.ui.theme.Green
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun TitleEditScreen(
    viewModel: TitleEditViewModel = hiltViewModel(),
    onNavigateBackWithResult: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = state.title) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.NavigateBackWithResult) {
                onNavigateBackWithResult(TITLE_KEY, state.title)
            } else if (event is UiEvent.NavigateBack) {
                onNavigateBack()
            }
        }
    }
    TitleEditScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TitleEditScreenContent(
    state: TitleEditState,
    onEvent: (TitleEditEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit_title),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                },
                    navigationIcon = {
                        IconButton(onClick = {
                            onEvent(TitleEditEvent.OnBackClick)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.chevron_left),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    actions = {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(end = dimensions.size16)
                                .clickable {
                                    onEvent(TitleEditEvent.OnSaveClick)
                                },
                            color = Green
                        )
                    }
                )
                Divider(color = LightGray)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
        ) {
            TextArea(value = state.title, onValueChange =
            { title ->
                onEvent(TitleEditEvent.OnTitleEnter(title))
            }, placeholder = stringResource(id = R.string.enter_title)
            )
        }

    }
}

@Composable
@Preview
private fun TitleEditScreenContentPreview() {
    TaskyTheme {
        TitleEditScreenContent(
            state = TitleEditState("Title"),
            onEvent = {}
        )
    }
}