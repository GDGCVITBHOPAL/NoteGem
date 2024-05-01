package com.gdsc_vitbhopal.notegem.controller.grocery

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.util.date.fullDate
import java.util.Calendar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GroceryEntryDetailsScreen(
    navController: NavHostController,
    entryId: Int,
    viewModel: GroceryViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        if (entryId != -1) {
            viewModel.onEvent(GroceryEvent.GetEntry(entryId))
        }
    }
    val state = viewModel.uiState
    val scaffoldState = rememberScaffoldState()
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    var title by rememberSaveable { mutableStateOf(state.entry?.title ?: "") }
    var content by rememberSaveable { mutableStateOf(state.entry?.content ?: "") }
    var date by rememberSaveable {
        mutableLongStateOf(
            state.entry?.createdDate ?: System.currentTimeMillis()
        )
    }

    LaunchedEffect(state.entry) {
        if (state.entry != null) {
            title = state.entry.title
            content = state.entry.content
            date = state.entry.createdDate
//            mood = state.entry.mood
        }
    }
    LaunchedEffect(state) {
        if (state.navigateUp) {
            openDialog = false
            navController.popBackStack(route = Screen.GroceryScreen.route, inclusive = false)
        }
        if (state.error != null) {
            scaffoldState.snackbarHostState.showSnackbar(
                state.error
            )
            viewModel.onEvent(GroceryEvent.ErrorDisplayed)
        }
    }
    BackHandler {
        if (state.entry != null) {
            val entry = state.entry.copy(
                title = title,
                content = content,
                createdDate = date,
                updatedDate = System.currentTimeMillis()
            )
            if (entryChanged(
                    state.entry,
                    entry
                )
            ) viewModel.onEvent(GroceryEvent.UpdateEntry(entry))
            else navController.popBackStack(route = Screen.GroceryScreen.route, inclusive = false)
        } else
            navController.popBackStack(route = Screen.GroceryScreen.route, inclusive = false)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    if (state.entry != null) IconButton(onClick = { openDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(R.string.delete_entry)
                        )
                    }
                    TextButton(onClick = {
                        showDatePicker(
                            Calendar.getInstance().apply { timeInMillis = date },
                            context,
                            onDateSelected = {
                                date = it
                            }
                        )
                    }) {
                        Text(
                            text = date.fullDate(),
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        },
        floatingActionButton = {
            if (state.entry == null)
                FloatingActionButton(
                    onClick = {
                        val entry = GroceryEntry(
                            title = title,
                            content = content,
                            createdDate = date,
                            updatedDate = System.currentTimeMillis()
                        )
                        viewModel.onEvent(GroceryEvent.AddEntry(entry))

                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_save),
                        contentDescription = stringResource(R.string.save_entry),
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(text = stringResource(R.string.title)) },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(text = stringResource(R.string.content)) },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (openDialog)
            AlertDialog(
                shape = RoundedCornerShape(25.dp),
                onDismissRequest = { openDialog = false },
                title = { Text(stringResource(R.string.delete_diary_entry_confirmation_title)) },
                text = {
                    Text(
                        stringResource(
                            R.string.delete_diary_entry_confirmation_message
                        )
                    )
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                            viewModel.onEvent(GroceryEvent.DeleteEntry(state.entry!!))
                        },
                    ) {
                        Text(
                            stringResource(R.string.delete_entry),
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                            openDialog = false
                        }) {
                        Text(
                            stringResource(R.string.cancel),
                            color = Color.White
                        )
                    }
                }
            )
    }
}

private fun entryChanged(
    entry: GroceryEntry?,
    newEntry: GroceryEntry
): Boolean {
    return entry?.title != newEntry.title ||
            entry.content != newEntry.content ||
            entry.createdDate != newEntry.createdDate
}

private fun showDatePicker(
    date: Calendar,
    context: Context,
    onDateSelected: (Long) -> Unit
) {

    val tempDate = Calendar.getInstance()
    val timePicker = TimePickerDialog(
        context,
        { _, hour, minute ->
            tempDate[Calendar.HOUR_OF_DAY] = hour
            tempDate[Calendar.MINUTE] = minute
            onDateSelected(tempDate.timeInMillis)
        }, date[Calendar.HOUR_OF_DAY], date[Calendar.MINUTE], false
    )
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            tempDate[Calendar.YEAR] = year
            tempDate[Calendar.MONTH] = month
            tempDate[Calendar.DAY_OF_MONTH] = day
            timePicker.show()
        },
        date[Calendar.YEAR],
        date[Calendar.MONTH],
        date[Calendar.DAY_OF_MONTH]
    )
    datePicker.show()
}