package com.example.simple_diary_planner.core_base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.simple_diary_planner.core_navigation.DiaryNavigation

abstract class BaseScreen<VM : BaseViewModel> {
    lateinit var viewModel: VM
    lateinit var navigaton: DiaryNavigation

    @Composable
    abstract fun Screen()

    @Composable
    fun Create(viewModel: VM, navController: NavHostController){
        this@BaseScreen.viewModel = viewModel
        this@BaseScreen.navigaton = DiaryNavigation(navController)

        val error by viewModel.error.observeAsState()

        if (error != null) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.removeError()
                },
                title = {},
                text = {
                    Text(text = error ?: "")
                },
                properties = DialogProperties(),
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { viewModel.removeError() }) {
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }
        Screen()
    }
}
