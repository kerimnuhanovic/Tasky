package com.taskyproject.tasky.presentation.eventdetails

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.taskyproject.tasky.R
import com.taskyproject.tasky.domain.model.VisitorOption
import com.taskyproject.tasky.presentation.components.AddVisitorDialog
import com.taskyproject.tasky.presentation.components.DatePickerWithDialog
import com.taskyproject.tasky.presentation.components.Dropdown
import com.taskyproject.tasky.presentation.components.TimePickerWithDialog
import com.taskyproject.tasky.presentation.components.VisitorCard
import com.taskyproject.tasky.presentation.components.VisitorsWidget
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.presentation.util.formatDate
import com.taskyproject.tasky.ui.theme.DarkGray
import com.taskyproject.tasky.ui.theme.Gray
import com.taskyproject.tasky.ui.theme.Light
import com.taskyproject.tasky.ui.theme.LightBlue
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LightRed
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun EventDetailsScreen(
    viewModel: EventDetailsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    title: StateFlow<String?>,
    description: StateFlow<String?>
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        launch {
            title.filterNotNull().collect {
                viewModel.onEvent(EventDetailsEvent.OnTitleChange(it))
            }
        }
        launch {
            description.filterNotNull().collect {
                viewModel.onEvent(EventDetailsEvent.OnDescriptionChange(it))
            }
        }
        launch {
            viewModel.uiEvent.collect { event ->
                if (event is UiEvent.Navigate) {
                    onNavigate(event)
                }
                else if (event is UiEvent.ShowToast) {
                    Toast.makeText(context, context.getString(event.message.valueId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    EventDetailsScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun EventDetailsScreenContent(
    state: EventDetailsState,
    onEvent: (EventDetailsEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            uriList.map {
                onEvent(EventDetailsEvent.OnEventPhotosSelect(uriList))
            }
        }
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
                                onEvent(EventDetailsEvent.OnCloseEditClick)
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
                            modifier = Modifier.padding(end = dimensions.size16).clickable {
                                onEvent(EventDetailsEvent.OnSaveClick)
                            }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    onEvent(EventDetailsEvent.OnEditClick)
                                }
                                .padding(end = dimensions.size8)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightRed
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
            if (state.isStartDatePickerOpened || state.isEndDatePickerOpened) {
                DatePickerWithDialog(
                    state = datePickerState,
                    onConfirmClick = {
                        if (state.isStartDatePickerOpened) {
                            onEvent(EventDetailsEvent.OnStartDateSelect(datePickerState.selectedDateMillis!!))
                        } else {
                            onEvent(EventDetailsEvent.OnEndDateSelect(datePickerState.selectedDateMillis!!))
                        }
                    }
                )
            }
            if (state.isStartTimePickerOpened || state.isEndTimePickerOpened) {
                TimePickerWithDialog(
                    title = "Select time",
                    state = timePickerState,
                    onConfirmClick = {
                        if (state.isStartTimePickerOpened) {
                            onEvent(
                                EventDetailsEvent.OnStartTimeSelect(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            )
                        } else {
                            onEvent(
                                EventDetailsEvent.OnEndTimeSelect(
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            )
                        }
                    })
            }
            if (state.isVisitorDialogOpened) {
                AddVisitorDialog(
                    value = state.visitorEmail,
                    onValueChange = { visitorEmail ->
                        onEvent(EventDetailsEvent.OnVisitorEmailEnter(visitorEmail))
                    },
                    onSubmit = {
                        onEvent(EventDetailsEvent.OnAddVisitorClick)
                    },
                    onExitClick = {
                        onEvent(EventDetailsEvent.OnVisitorDialogCloseClick)
                    },
                    errorMessage = if (state.visitorErrorMessageId != null) stringResource(id = state.visitorErrorMessageId) else null,
                    isLoading = state.isVisitorAdditionInProgress
                )
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
                    tint = LightRed
                )
                Text(
                    text = stringResource(id = R.string.event),
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
                            onEvent(EventDetailsEvent.OnEditTitleClick)
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
                            onEvent(EventDetailsEvent.OnEditDescriptionClick)
                        }
                    )
                }
            }
            Row(
                horizontalArrangement = if (state.eventPhotos.isEmpty()) Arrangement.Center else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = dimensions.size32)
                    .fillMaxWidth()
                    .heightIn(min = dimensions.size110)
                    .background(LightGray)
                    .clickable {
                        if (state.eventPhotos.isEmpty()) {
                            galleryLauncher.launch("image/*")
                        }
                    }
            ) {
                if (state.eventPhotos.isEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        tint = LightBlue
                    )
                    Text(
                        text = stringResource(id = R.string.add_photos),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = LightBlue,
                        modifier = Modifier.padding(start = dimensions.size16)
                    )
                } else {
                    Column(
                        modifier = Modifier.padding(horizontal = dimensions.size16)
                    ) {
                        Spacer(modifier = Modifier.height(dimensions.size16))
                        Text(
                            text = stringResource(id = R.string.photos),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = dimensions.size16)
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(dimensions.size8)
                        ) {
                            state.eventPhotos.forEach { image ->
                                Column {
                                    Image(
                                        modifier = Modifier
                                            .height(dimensions.size60)
                                            .width(dimensions.size60)
                                            .border(
                                                width = dimensions.size2,
                                                color = LightBlue
                                            ),
                                        painter = rememberAsyncImagePainter(model = image),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(dimensions.size8))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .height(dimensions.size60)
                                    .width(dimensions.size60)
                                    .border(
                                        width = dimensions.size2,
                                        color = LightBlue
                                    )
                                    .clickable {
                                        galleryLauncher.launch("image/*")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = null,
                                    tint = LightBlue
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(dimensions.size16))
                    }
                }
            }
            Divider(
                color = Light,
                modifier = Modifier.padding(
                    start = dimensions.size16,
                    end = dimensions.size16,
                    top = dimensions.size32
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
                        text = stringResource(id = R.string.from),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(dimensions.size50)
                    )
                    Spacer(modifier = Modifier.width(dimensions.size16))
                    Text(
                        text = state.fromTime.toString(),
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
                                onEvent(EventDetailsEvent.OnStartTimeClick)
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
                    Spacer(modifier = Modifier.width(dimensions.size16))
                    Text(
                        text = state.fromDate.formatDate(),
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
                                onEvent(EventDetailsEvent.OnStartDateClick)
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
                        text = stringResource(id = R.string.to),
                        color = Color.Black,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(dimensions.size50)
                    )
                    Spacer(modifier = Modifier.width(dimensions.size16))
                    Text(
                        text = state.toTime.toString(),
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
                                onEvent(EventDetailsEvent.OnEndTimeClick)
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
                    Spacer(modifier = Modifier.width(dimensions.size16))
                    Text(
                        text = state.toDate.formatDate(),
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
                                onEvent(EventDetailsEvent.OnEndDateClick)
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
                        onEvent(EventDetailsEvent.OnReminderDropdownStateChange)
                    },
                    onItemSelect = { option ->
                        onEvent(EventDetailsEvent.OnReminderOptionSelect(option))
                    }
                )
            }
            Spacer(modifier = Modifier.height(dimensions.size32))
            Row(
                modifier = Modifier
                    .height(dimensions.size30),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.visitors),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = dimensions.size16)
                )
                if (state.isEditable) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(dimensions.size30)
                            .background(LightGray)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            tint = LightBlue,
                            modifier = Modifier.clickable {
                                onEvent(EventDetailsEvent.OnVisitorDialogOpenClick)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensions.size32))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensions.size16)
            ) {
                VisitorsWidget(
                    onClick = {
                        onEvent(EventDetailsEvent.OnVisitorOptionChange(VisitorOption.All))
                    },
                    visitorOption = VisitorOption.All,
                    isSelected = state.selectedVisitorOption is VisitorOption.All
                )
                VisitorsWidget(
                    onClick = {
                        onEvent(EventDetailsEvent.OnVisitorOptionChange(VisitorOption.Going))
                    },
                    visitorOption = VisitorOption.Going,
                    isSelected = state.selectedVisitorOption is VisitorOption.Going
                )
                VisitorsWidget(
                    onClick = {
                        onEvent(EventDetailsEvent.OnVisitorOptionChange(VisitorOption.NotGoing))
                    },
                    visitorOption = VisitorOption.NotGoing,
                    isSelected = state.selectedVisitorOption is VisitorOption.NotGoing
                )
            }
            if (state.selectedVisitorOption is VisitorOption.All || state.selectedVisitorOption is VisitorOption.Going) {
                Text(
                    text = stringResource(id = R.string.going),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = DarkGray,
                    modifier = Modifier.padding(dimensions.size16)
                )
                state.attendees.forEach { eventAttendee ->
                    if (eventAttendee.isGoing) {
                        VisitorCard(fullName = eventAttendee.fullName, isCreator = false)
                    }
                }
            }
            if (state.selectedVisitorOption is VisitorOption.All || state.selectedVisitorOption is VisitorOption.NotGoing) {
                Text(
                    text = stringResource(id = R.string.not_going),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = DarkGray,
                    modifier = Modifier.padding(dimensions.size16)
                )
                state.attendees.forEach { eventAttendee ->
                    if (!eventAttendee.isGoing) {
                        VisitorCard(
                            fullName = eventAttendee.fullName,
                            isCreator = false,
                            modifier = Modifier.padding(horizontal = dimensions.size16)
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview
private fun EventDetailsScreenPreview() {
    TaskyTheme {
        EventDetailsScreenContent(state = EventDetailsState(
            isEditable = true,
        ), onEvent = {})
    }
}