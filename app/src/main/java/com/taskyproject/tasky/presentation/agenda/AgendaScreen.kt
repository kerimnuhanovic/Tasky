package com.taskyproject.tasky.presentation.agenda

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskyproject.tasky.R
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.MenuItem
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.presentation.components.ActionConfirmationDialog
import com.taskyproject.tasky.presentation.components.Calendar
import com.taskyproject.tasky.presentation.components.EventItem
import com.taskyproject.tasky.presentation.components.ReminderItem
import com.taskyproject.tasky.presentation.components.TaskItem
import com.taskyproject.tasky.presentation.util.AgendaItemType
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.ui.theme.Black
import com.taskyproject.tasky.ui.theme.LightBlue
import com.taskyproject.tasky.ui.theme.LightPurple
import com.taskyproject.tasky.ui.theme.LightRed
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.PrimaryBlue
import com.taskyproject.tasky.ui.theme.TaskyTheme
import kotlinx.coroutines.launch

@Composable
fun AgendaScreen(
    viewModel: AgendaViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateWithPopup: (UiEvent.NavigateWithPopup) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(event)
            } else if (event is UiEvent.ShowToast) {
                Toast.makeText(
                    context,
                    context.getString(event.message.valueId),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (event is UiEvent.NavigateWithPopup) {
                onNavigateWithPopup(event)
            }
        }
    }

    val activity = LocalView.current.context as Activity
    val backgroundArgb = MaterialTheme.colorScheme.background.toArgb()
    activity.window.statusBarColor = backgroundArgb

    AgendaScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgendaScreenContent(
    state: AgendaState,
    onEvent: (AgendaEvent) -> Unit
) {
    val dimensions = LocalDimensions.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(AgendaEvent.OnAddClick) },
                shape = RoundedCornerShape(dimensions.size16),
                containerColor = Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            if (state.isConfirmationModalOpen) {
                item {
                    ActionConfirmationDialog(
                        titleId = R.string.delete,
                        descriptionId = R.string.delete_description,
                        icon = Icons.Outlined.Delete,
                        onConfirm = {
                            onEvent(AgendaEvent.OnConfirmDeleteClick)
                        },
                        onDismiss = {
                            onEvent(AgendaEvent.OnDismissModalClick)
                        }
                    )
                }
            }
            if (state.isLogoutModalOpened) {
                item {
                    ActionConfirmationDialog(
                        titleId = R.string.logout,
                        descriptionId = R.string.logout_description,
                        icon = Icons.Default.ExitToApp,
                        onConfirm = {
                            onEvent(AgendaEvent.OnLogoutConfirmClick)
                        },
                        onDismiss = {
                            onEvent(AgendaEvent.OnLogoutDismissClick)
                        }
                    )
                }
            }
            item {
                Calendar(
                    onDateSelect = { selectedDate ->
                        onEvent(AgendaEvent.OnDateChange(selectedDate))
                    },
                    isExpanded = state.isLogoutMenuExpanded,
                    onExpandChange = {
                        onEvent(AgendaEvent.OnLogoutMenuClick)
                    },
                    menuItems = listOf(
                        MenuItem(label = stringResource(id = R.string.logout), onItemClick = {
                            onEvent(AgendaEvent.OnLogoutClick)
                        })
                    ),
                    userInitials = state.userInitials
                )
            }
            item {
                Spacer(modifier = Modifier.height(dimensions.size16))
            }
            itemsIndexed(state.agendaItems) { index, agendaItem ->
                when (agendaItem) {
                    is Event -> {
                        EventItem(
                            event = agendaItem,
                            isMenuOpen = state.indexOfOpenedMenu == index,
                            menuItems = listOf(
                                MenuItem(label = stringResource(id = R.string.open), onItemClick = {
                                    onEvent(AgendaEvent.OnOpenAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(label = stringResource(id = R.string.edit), onItemClick = {
                                    onEvent(AgendaEvent.OnEditAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(
                                    label = stringResource(id = R.string.delete),
                                    onItemClick = {
                                        onEvent(AgendaEvent.OnDeleteAgendaItemClick(agendaItem))
                                    }
                                )
                            ),
                            onExpandChange = {
                                onEvent(AgendaEvent.OnMoreClick(index))
                            },
                            modifier = Modifier.padding(horizontal = dimensions.size16)
                        )
                    }

                    is Reminder -> {
                        ReminderItem(
                            reminder = agendaItem,
                            isMenuOpen = state.indexOfOpenedMenu == index,
                            menuItems = listOf(
                                MenuItem(label = stringResource(id = R.string.open), onItemClick = {
                                    onEvent(AgendaEvent.OnOpenAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(label = stringResource(id = R.string.edit), onItemClick = {
                                    onEvent(AgendaEvent.OnEditAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(
                                    label = stringResource(id = R.string.delete),
                                    onItemClick = {
                                        onEvent(AgendaEvent.OnDeleteAgendaItemClick(agendaItem))
                                    }
                                )
                            ),
                            onExpandChange = {
                                onEvent(AgendaEvent.OnMoreClick(index))
                            },
                            modifier = Modifier.padding(horizontal = dimensions.size16)
                        )
                    }

                    is Task -> {
                        TaskItem(
                            task = agendaItem,
                            isMenuOpen = state.indexOfOpenedMenu == index,
                            menuItems = listOf(
                                MenuItem(label = stringResource(id = R.string.open), onItemClick = {
                                    onEvent(AgendaEvent.OnOpenAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(label = stringResource(id = R.string.edit), onItemClick = {
                                    onEvent(AgendaEvent.OnEditAgendaItemClick(agendaItem))
                                }
                                ),
                                MenuItem(
                                    label = stringResource(id = R.string.delete),
                                    onItemClick = {
                                        onEvent(AgendaEvent.OnDeleteAgendaItemClick(agendaItem))
                                    }
                                )
                            ),
                            onExpandChange = {
                                onEvent(AgendaEvent.OnMoreClick(index))
                            },
                            modifier = Modifier.padding(horizontal = dimensions.size16)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensions.size8))
            }
        }
        if (state.isBottomSheetOpened) {
            ModalBottomSheet(
                onDismissRequest = {
                    onEvent(AgendaEvent.OnBottomSheetDismiss)
                },
                sheetState = sheetState
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensions.size46)
                        .padding(horizontal = dimensions.size16)
                        .clickable {
                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onEvent(AgendaEvent.OnAddNewAgendaItemClick(AgendaItemType.EVENT))
                                    }
                                }
                        }

                ) {
                    Text(
                        text = stringResource(id = R.string.dot),
                        style = MaterialTheme.typography.titleSmall,
                        color = LightRed,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.event),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = dimensions.size8)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensions.size46)
                        .padding(horizontal = dimensions.size16)
                        .clickable {
                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onEvent(AgendaEvent.OnAddNewAgendaItemClick(AgendaItemType.REMINDER))
                                    }
                                }
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.dot),
                        style = MaterialTheme.typography.titleSmall,
                        color = LightPurple,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.reminder),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = dimensions.size8)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensions.size46)
                        .padding(horizontal = dimensions.size16)
                        .clickable {
                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onEvent(AgendaEvent.OnAddNewAgendaItemClick(AgendaItemType.TASK))
                                    }
                                }
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.dot),
                        style = MaterialTheme.typography.titleSmall,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.task),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = dimensions.size8)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreenContent(
            state = AgendaState(
                userInitials = "KN",
                indexOfOpenedMenu = null,
                agendaItems = listOf(
                    Event(
                        id = "eventId",
                        isUserEventCreator = true,
                        title = "Sprint Planning",
                        description = "Bi-Weekly Sprint Planning",
                        from = 1725292800000,
                        to = 1725295500000,
                        host = "",
                        remindAt = 1725209100000,
                        photos = emptyList(),
                        attendees = emptyList()
                    ),
                    Task(
                        id = "",
                        title = "Work on Tasky app",
                        description = "Implement Task Item component",
                        time = 1725295500000,
                        remindAt = 1725209100000,
                        isDone = true
                    ),
                    Reminder(
                        id = "",
                        title = "Android Dev Summit",
                        description = "Topics: Async Programming",
                        time = 1725295500000,
                        remindAt = 1725209100000
                    )
                )
            ),
            onEvent = {},
        )
    }
}