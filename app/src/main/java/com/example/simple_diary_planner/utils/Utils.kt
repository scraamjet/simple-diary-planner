package com.example.simple_diary_planner.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {

    fun Long.withTime(time: String): Long {
        val dateTime = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .withHour(time.substringBefore(":").toInt())
            .withMinute(time.substringAfter(":").toInt())
            .toInstant()
        return dateTime.toEpochMilli()
    }

    fun Long.formatDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
            .withZone(ZoneId.systemDefault())
        return formatter.format(Instant.ofEpochMilli(this))
    }
}
