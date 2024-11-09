package com.example.simple_diary_planner.feature_task_detail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simple_diary_planner.core_base.BaseScreen
import com.example.simple_diary_planner.feature_calendar.presentation.formatDate
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout

class TaskDetailScreen : BaseScreen<TaskDetailViewModel>() {

    @Composable
    override fun Screen() {
        val taskId = remember { navigaton.getTaskIdFromCurrentRoute() }
        val state by viewModel.state.collectAsState()

        LaunchedEffect(taskId) {
            taskId?.let { viewModel.loadTaskById(it.toInt()) }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            state.task?.let { taskDetail ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val (backButton, title, date, description) = createRefs()

                    IconButton(
                        onClick = { navigaton.navigateBack() },
                        modifier = Modifier.padding(top = 16.dp)
                            .constrainAs(backButton) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Text(
                        text = taskDetail.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.constrainAs(title) {
                            top.linkTo(backButton.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                        }
                    )

                    Text(
                        text = "Start: ${taskDetail.dateStart.formatDate()} - End: ${taskDetail.dateFinish.formatDate()}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(date) {
                            top.linkTo(title.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                        }
                    )

                    Text(
                        text = taskDetail.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.constrainAs(description) {
                            top.linkTo(date.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                        }
                    )
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.errorMessage ?: "Error loading task")
                }
            }
        }
    }
}


fun Long.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(this))
}




