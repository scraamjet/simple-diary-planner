package com.example.simple_diary_planner.core_data.json

import android.content.Context
import com.example.simple_diary_planner.core_model.TaskEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonDataSource(private val context: Context) {

    suspend fun loadTasks(): List<TaskEntity> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("tasks.json").use { it.readBytes().toString(Charsets.UTF_8) }
        val taskType = object : TypeToken<List<TaskEntity>>() {}.type
        Gson().fromJson(jsonString, taskType)
    }
}
