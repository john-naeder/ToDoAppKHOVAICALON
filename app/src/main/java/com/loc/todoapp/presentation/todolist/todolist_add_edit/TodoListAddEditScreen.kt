package com.loc.todoapp.presentation.todolist.todolist_add_edit

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.todoapp.util.StringAndDateConvertor.Companion.isValidDate
import com.loc.todoapp.util.UIEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoListAddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: TodoListAddEditViewModel = hiltViewModel()
) {
    var isStartDateValid by remember { mutableStateOf(true) }
    var isEndDateValid by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }

                else -> Unit
            }
        }
    }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedDate)
        }
    }
    val formattedEndDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val formattedEndTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val dateEndDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val timeEndDialogState = rememberMaterialDialogState()
    val context = LocalContext.current
    val dialogState = rememberMaterialDialogState()
    var pickedEndDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedEndTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val endDateDialogState = rememberMaterialDialogState()
    val endTimeDialogState = rememberMaterialDialogState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isStartDateValid && isEndDateValid)
                    viewModel.onEvent(TodoListAddEditEvent.OnSaveTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(TodoListAddEditEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(TodoListAddEditEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.startDate,
                onValueChange = { input ->
                    isStartDateValid = isValidDate(input)
                    viewModel.onEvent(TodoListAddEditEvent.OnStartDateChange(input))
                },
                placeholder = {
                    Text(text = "Start at: dd-MM-yyyy")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        if (!isStartDateValid) Color.Red else Color.Gray,
                        RoundedCornerShape(8.dp)
                    ),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.endDate,
                onValueChange = { input ->
                    isEndDateValid = isValidDate(input)
                    viewModel.onEvent(TodoListAddEditEvent.OnEndDateChange(input))
                },
                placeholder = {
                    Text(text = "Due to: dd-MM-yyyy")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        if (!isEndDateValid) Color.Red else Color.Gray,
                        RoundedCornerShape(8.dp)
                    ),
                singleLine = false,
                maxLines = 5
            )
            if (!isEndDateValid || !isStartDateValid) {
                Text(
                    text = "Invalid date format. Please enter date in dd-MM-yyyy format.",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Lịch"
                    )
                    Text(text = "Ngày bắt đầu: $formattedDate")
                }
                Button(onClick = {
                    timeDialogState.show()
                }) {
                    Text(text = "Giờ: $formattedTime")
                }
            }
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Hoàn tất") {
                        Toast.makeText(
                            context,
                            "Thêm ngày thành công",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Hủy")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Chọn ngày",
                    allowedDateValidator = {
                        it.dayOfMonth % 2 == 1
                    }
                ) {
                    pickedDate = it
                }
            }
            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(text = "Hoàn tất") {
                        Toast.makeText(
                            context,
                            "Thêm giờ thành công",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Huỷ")
                }
            ) {
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Chọn giờ",
                    timeRange = LocalTime.MIDNIGHT..LocalTime.MAX,
                    is24HourClock = true
                ) {
                    pickedTime = it
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(onClick = {
                    dateEndDialogState.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Lịch"
                    )
                    Text(text = "Ngày kết thúc: $formattedEndDate")
                }
                Button(onClick = {
                    timeEndDialogState.show()
                }) {
                    Text(text = "Giờ: $formattedEndTime")
                }
            }
        }


        MaterialDialog(
            dialogState = endDateDialogState,
            buttons = {
                positiveButton(text = "Hoàn tất") {
                    Toast.makeText(
                        context,
                        "Thêm ngày kết thúc thành công",
                        Toast.LENGTH_LONG
                    ).show()
                }
                negativeButton(text = "Hủy")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now().plusDays(1),
                title = "Chọn ngày kết thúc",
                allowedDateValidator = {
                    it.dayOfMonth % 2 == 1
                }
            ) {
                pickedEndDate = it
            }
        }
        MaterialDialog(
            dialogState = endTimeDialogState,
            buttons = {
                positiveButton(text = "Hoàn tất") {
                    Toast.makeText(
                        context,
                        "Thêm giờ kết thúc thành công",
                        Toast.LENGTH_LONG
                    ).show()
                }
                negativeButton(text = "Huỷ")
            }
        ) {
            timepicker(
                initialTime = LocalTime.NOON,
                title = "Chọn giờ kết thúc",
                timeRange = LocalTime.MIDNIGHT..LocalTime.MAX,
                is24HourClock = true
            ) {
                pickedEndTime = it
            }
        }
    }
}


