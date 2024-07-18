package com.example.simple_diary_planner.feature_calendar.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simple_diary_planner.core_base.BaseScreen
import com.example.simple_diary_planner.core_model.Task
import com.example.simple_diary_planner.feature_calendar.components.BasicCalendar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CalendarScreen : BaseScreen<CalendarViewModel>() {

    @SuppressLint("SuspiciousIndentation")
    @Composable
    override fun Screen() {
        val state by viewModel.state.collectAsState()
        val tasks = state.tasks
        val selectedDate = state.selectedDate

        Column(modifier = Modifier.padding(8.dp)) {
            BasicCalendar(
                selectedDate = selectedDate,
                onDateSelected = { date -> viewModel.onSelectDate(CalendarEvent.SelectDate(date)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks) { task ->
                    TaskItem(task = task, onClick = { navigaton.navigateToTaskDetail(task.id) })
                }
            }

            Button(
                onClick = { navigaton.navigateToCreateTask() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Create Task", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.name, style = typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Start: ${task.dateStart.formatDate()}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "End: ${task.dateFinish.formatDate()}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun Long.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).format(formatter)
}