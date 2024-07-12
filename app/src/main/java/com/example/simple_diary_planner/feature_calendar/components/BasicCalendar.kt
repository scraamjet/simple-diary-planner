package com.example.simple_diary_planner.feature_calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.style.TextAlign
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId

@Composable
fun BasicCalendar(
    modifier: Modifier = Modifier,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    val currentDate = remember { mutableStateOf(LocalDate.now()) }
    val selectedLocalDate = remember { mutableStateOf(Instant.ofEpochMilli(selectedDate).atZone(ZoneId.systemDefault()).toLocalDate()) }
    val daysInMonth = remember(currentDate.value) {
        val yearMonth = YearMonth.of(currentDate.value.year, currentDate.value.month)
        yearMonth.lengthOfMonth()
    }

    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentDate.value = currentDate.value.minusMonths(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
            }
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = "${currentDate.value.month} ${currentDate.value.year}",
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(onClick = { currentDate.value = currentDate.value.plusMonths(1) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEach { day ->
                Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        val firstDayOfMonth = LocalDate.of(currentDate.value.year, currentDate.value.month, 1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value - 1
        val totalSlots = daysInMonth + firstDayOfWeek

        LazyVerticalGrid(columns = GridCells.Fixed(7),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(totalSlots) { index ->
                if (index >= firstDayOfWeek) {
                    val day = index - firstDayOfWeek + 1
                    val date = LocalDate.of(currentDate.value.year, currentDate.value.month, day)
                    DayItem(
                        day = day,
                        isSelected = date == selectedLocalDate.value,
                        isToday = date == LocalDate.now(),
                        onClick = {
                            selectedLocalDate.value = date
                            onDateSelected(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}
@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5

@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val backgroundColor = when {
        isSelected -> if (colors.isLight()) Color(0xFF6200EE) else Color.Green
        isToday -> Color.LightGray
        else -> Color.Transparent
    }

    val textColor = if (colors.isLight()) Color.Black else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor, CircleShape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = day.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
