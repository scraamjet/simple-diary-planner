package com.example.simple_diary_planner.feature_create_task.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.simple_diary_planner.core_base.BaseScreen
import com.example.simple_diary_planner.feature_create_task.components.ForwardOnlyCalendar

class CreateTaskScreen : BaseScreen<CreateTaskViewModel>() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Screen() {
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current
        var dropdownExpanded by remember { mutableStateOf(false) }
        var selectedTime by remember { mutableStateOf(timeSlots[0]) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .imePadding()
        ) {
            IconButton(
                onClick = { navigaton.navigateBack() },
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Task Name") },
                isError = state.name.length < 3,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            if (state.name.length !in 3..22) {
                Text(
                    "Name must be from 3 to 22 characters",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                label = { Text("Task Description") },
                isError = state.description.length < 5,
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            if (state.description.length !in 5..40) {
                Text(
                    "Description must be from 5 to 40 characters",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            ForwardOnlyCalendar(
                selectedDate = state.selectedDate,
                onDateSelected = {
                    viewModel.onDateChange(it)
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val timeSlots = listOf(
                "00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00",
                "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00",
                "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00",
                "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
                "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
                "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"
            )
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    readOnly = true,
                    value = selectedTime,
                    onValueChange = {},
                    label = { Text("Time") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = dropdownExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    timeSlots.forEachIndexed { index, timeSlot ->
                        DropdownMenuItem(
                            text = { Text(timeSlot) },
                            onClick = {
                                selectedTime = timeSlots[index]
                                viewModel.onStartTimeChange(timeSlot.substringBefore("-"))
                                viewModel.onEndTimeChange(timeSlot.substringAfter("-"))
                                dropdownExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.createTask() },
                enabled = state.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("Create Task")
            }

            state.event?.let { event ->
                when (event) {
                    is CreateTaskEvent.TaskCreated -> {
                        navigaton.navigateBack()
                        viewModel.clearEvent()
                        Toast.makeText(context, "Task created successfully", Toast.LENGTH_SHORT).show()
                    }
                    is CreateTaskEvent.ShowError -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    companion object {
        val timeSlots = listOf(
            "00:00-01:00", "01:00-02:00", "02:00-03:00", "03:00-04:00",
            "04:00-05:00", "05:00-06:00", "06:00-07:00", "07:00-08:00",
            "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00",
            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00",
            "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00",
            "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-00:00"
        )
    }
}