package com.gdsc_vitbhopal.notegem.controller.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.ui.theme.ModerateBlueCard
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import com.gdsc_vitbhopal.notegem.util.settings.Order
import com.gdsc_vitbhopal.notegem.util.settings.OrderType
import com.google.accompanist.flowlayout.FlowRow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val uiState = viewModel.notesUiState
    var orderSettingsVisible by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.notes),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.NoteDetailsScreen.route.replace(
                            "{${com.gdsc_vitbhopal.notegem.util.Constants.NOTE_ID_ARG}}",
                            "${-1}"
                        )
                    )
                },
                backgroundColor = ModerateBlueCard,
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add_note),
                    tint = Color.White
                )
            }
        },
    ) {
        if (uiState.notes.isEmpty())
            NoNotesMessage()
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { orderSettingsVisible = !orderSettingsVisible }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.ic_settings_sliders),
                        contentDescription = stringResource(R.string.order_by)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Screen.NoteSearchScreen.route)
                }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
            AnimatedVisibility(visible = orderSettingsVisible) {
                NotesSettingsSection(
                    uiState.notesOrder,
                    uiState.noteView,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.UpdateOrder(it))
                    },
                    onViewChange = {
                        viewModel.onEvent(NoteEvent.UpdateView(it))
                    }
                )
            }
            if (uiState.noteView == ItemView.LIST){
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(uiState.notes, key = {it.id}) { note ->
                        NoteItem(
                            note = note,
                            onClick = {
                                navController.navigate(
                                    Screen.NoteDetailsScreen.route.replace(
                                        "{${com.gdsc_vitbhopal.notegem.util.Constants.NOTE_ID_ARG}}",
                                        "${note.id}"
                                    )
                                )
                            }
                        )
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
//                    cells = GridCells.Adaptive(150.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    columns = StaggeredGridCells.Adaptive(150.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ){
                    items(uiState.notes){ note ->
                        key(note.id) {
                            NoteItem(
                                note = note,
                                onClick = {
                                    navController.navigate(
                                        Screen.NoteDetailsScreen.route.replace(
                                            "{${Constants.NOTE_ID_ARG}}",
                                            "${note.id}"
                                        )
                                    )
                                },
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotesSettingsSection(order: Order, view: ItemView, onOrderChange: (Order) -> Unit, onViewChange: (ItemView) -> Unit) {
    val orders = listOf(
        Order.DateModified(),
        Order.DateCreated(),
        Order.Alphabetical()
    )
    val orderTypes = listOf(
        OrderType.ASC(),
        OrderType.DESC()
    )
    val noteViews = listOf(
        ItemView.LIST,
        ItemView.GRID
    )
    val bottomRoundedCorner = RoundedCornerShape(
        topStart = 30.dp,
        topEnd = 30.dp,
        bottomStart = 30.dp,
        bottomEnd = 30.dp
    )
    Column (modifier = Modifier.background(MaterialTheme.colors.surface, shape = bottomRoundedCorner)
        .padding(10.dp)){
        Text(
            text = stringResource(R.string.order_by),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp)
        )
        FlowRow(
            modifier = Modifier.padding(end = 8.dp)
        ) {
            orders.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = order.orderTitle == it.orderTitle,
                        onClick = {
                            if (order.orderTitle != it.orderTitle)
                                onOrderChange(
                                    it.copy(orderType = order.orderType)
                                )
                        }
                    )
                    Text(text = it.orderTitle, style = MaterialTheme.typography.body1)
                }
            }
        }
        Divider()
        FlowRow {
            orderTypes.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = order.orderType.orderTitle == it.orderTitle,
                        onClick = {
                            if (order.orderTitle != it.orderTitle)
                                onOrderChange(
                                    order.copy(it)
                                )
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = it.orderTitle, style = MaterialTheme.typography.body1)
                }
            }
        }
        Divider()
        Text(
            text = stringResource(R.string.view_as),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        FlowRow {
            noteViews.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = view.title == it.title,
                        onClick = {
                            if (view.title != it.title)
                                onViewChange(
                                    it
                                )
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(it.title), style = MaterialTheme.typography.body1)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun NoNotesMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_notes_yet),
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.notes),
            contentDescription = stringResource(R.string.no_notes_yet),
            alpha = 0.7f
        )
    }
}