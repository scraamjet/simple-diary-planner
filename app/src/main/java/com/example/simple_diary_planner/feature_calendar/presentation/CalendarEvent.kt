package com.example.simple_diary_planner.feature_calendar.presentation

sealed class CalendarEvent {
    data class SelectDate(val date: Long) : CalendarEvent()
}