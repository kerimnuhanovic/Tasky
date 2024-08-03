package com.taskyproject.tasky.presentation.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskyproject.tasky.R
import com.taskyproject.tasky.presentation.components.DatePickerWithDialog
import com.taskyproject.tasky.presentation.components.Dropdown
import com.taskyproject.tasky.presentation.components.TimePickerWithDialog
import com.taskyproject.tasky.presentation.eventdetails.EventDetailsEvent
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.presentation.util.formatDate
import com.taskyproject.tasky.ui.theme.DarkGray
import com.taskyproject.tasky.ui.theme.Gray
import com.taskyproject.tasky.ui.theme.Light
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LightRed
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.PrimaryBlue
import com.taskyproject.tasky.ui.theme.TaskyTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    title: StateFlow<String?>,
    description: StateFlow<String?>
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        launch {
            title.filterNotNull().collect {
                viewModel.onEvent(TaskEvent.OnTitleChange(it))
            }
        }
        launch {
            description.filterNotNull().collect {
                viewModel.onEvent(TaskEvent.OnDescriptionChange(it))
            }
        }
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is UiEvent.Navigate) {
                    onNavigate(event)
                } else if (event is UiEvent.ShowToast) {
                    Toast.makeText(
                        context,
                        context.getString(event.message.valueId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    TaskScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenContent(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = LightRed,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    if (state.isEditable) {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel_icon),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                onEvent(TaskEvent.OnCloseEditClick)
                            }
                        )
                    }
                },
                actions = {
                    if (state.isEditable) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(end = dimensions.size16)
                                .clickable {
                                    onEvent(TaskEvent.OnSaveClick)
                                }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    onEvent(TaskEvent.OnEditClick)
                                }
                                .padding(end = dimensions.size8)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clip(RoundedCornerShape(topEnd = dimensions.size30, topStart = dimensions.size30))
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            if (state.isDatePickerOpen) {
                DatePickerWithDialog(
                    state = datePickerState,
                    onConfirmClick = {
                        onEvent(TaskEvent.OnDateSelect(datePickerState.selectedDateMillis!!))
                    }
                )
            }
            if (state.isTimePickerOpen) {
                TimePickerWithDialog(
                    title = "Select time",
                    state = timePickerState,
                    onConfirmClick = {
                        onEvent(
                            TaskEvent.OnTimeSelect(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        )
                    })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = dimensions.size30,
                    start = dimensions.size16
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appointment),
                    contentDescription = null,
                    tint = PrimaryBlue
                )
                Text(
                    text = stringResource(id = R.string.task),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = dimensions.size10),
                    color = DarkGray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = dimensions.size50,
                    start = dimensions.size16,
                    end = dimensions.size16
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle_icon),
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = dimensions.size10),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                if (state.isEditable) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_right),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            onEvent(TaskEvent.OnEditTitleClick)
                        }
                    )
                }
            }
            Divider(
                color = Light,
                modifier = Modifier.padding(
                    start = dimensions.size16,
                    end = dimensions.size16,
                    top = dimensions.size20
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = dimensions.size16,
                    start = dimensions.size16,
                    end = dimensions.size16
                )
            ) {
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                if (state.isEditable) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_right),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            onEvent(TaskEvent.OnEditDescriptionClick)
                        }
                    )
                }
            }
            Divider(
                color = Light,
                modifier = Modifier.padding(
                    start = dimensions.size16,
                    end = dimensions.size16,
                    top = dimensions.size16
                )
            )
            Row(modifier = Modifier.padding(vertical = dimensions.size16)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(
                            start = dimensions.size16,
                            end = dimensions.size16
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.at),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(dimensions.size50)
                    )
                    Spacer(modifier = Modifier.width(dimensions.size16))
                    Text(
                        text = state.time.toString(),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (state.isEditable) {
                        Icon(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                onEvent(TaskEvent.OnEditTimeClick)
                            }
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensions.size16,
                            end = dimensions.size16
                        )
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.width(dimensions.size16))
                    Text(
                        text = state.date.formatDate(),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (state.isEditable) {
                        Icon(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                onEvent(TaskEvent.OnEditDateClick)
                            }
                        )
                    }
                }
            }
            Divider(
                color = Light,
                modifier = Modifier.padding(
                    start = dimensions.size16,
                    end = dimensions.size16
                )
            )
            Row(
                modifier = Modifier.padding(horizontal = dimensions.size16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensions.size4))
                        .size(dimensions.size30)
                        .background(LightGray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = null,
                        tint = Gray
                    )
                }
                Dropdown(
                    isExpanded = state.isReminderDropdownExpanded,
                    selectedItem = state.selectedReminderOption,
                    options = state.reminderOptions,
                    onExpandChange = {
                        onEvent(TaskEvent.OnReminderDropdownStateChange)
                    },
                    onItemSelect = { option ->
                        onEvent(TaskEvent.OnReminderOptionSelect(option))
                    }
                )
            }
            Spacer(modifier = Modifier.height(dimensions.size32))
        }
    }
}

@Composable
@Preview
private fun TaskScreenPreview() {
    TaskyTheme {
        TaskScreenContent(
            state = TaskState(),
            onEvent = {}
        )
    }
}