package com.example.simple_diary_planner.core_model

data class Task(
    val id: Int,
    val name: String,
    val dateStart: Long,
    val dateFinish: Long,
    val description: String
)
