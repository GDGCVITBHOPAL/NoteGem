package com.gdsc_vitbhopal.notegem.controller.bookmarks

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.gdsc_vitbhopal.notegem.ui.theme.SettingTileColor
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import com.gdsc_vitbhopal.notegem.util.settings.Order
import com.gdsc_vitbhopal.notegem.util.settings.OrderType
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarksScreen(
    navController: NavHostController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var orderSettingsVisible by remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.bookmarks),
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
                        Screen.BookmarkDetailScreen.route.replace(
                            "{${Constants.BOOKMARK_ID_ARG}}",
                            "${-1}"
                        )
                    )
                },
                backgroundColor = ModerateBlueCard,
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add_bookmark),
                    tint = Color.White
                )
            }
        },
    ) {
        if (uiState.bookmarks.isEmpty())
            NoBookmarksMessage()
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
                    navController.navigate(Screen.BookmarkSearchScreen.route)
                }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
            AnimatedVisibility(visible = orderSettingsVisible) {
                BookmarksSettingsSection(
                    uiState.bookmarksOrder,
                    uiState.bookmarksView,
                    onOrderChange = {
                        viewModel.onEvent(BookmarkEvent.UpdateOrder(it))
                    },
                    onViewChange = {
                        viewModel.onEvent(BookmarkEvent.UpdateView(it))
                    }
                )
            }
            if (uiState.bookmarksView == ItemView.LIST) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(uiState.bookmarks, key = { it.id }) { bookmark ->
                        BookmarkItem(
                            bookmark = bookmark,
                            onClick = {
                                navController.navigate(
                                    Screen.BookmarkDetailScreen.route.replace(
                                        "{${Constants.BOOKMARK_ID_ARG}}",
                                        "${bookmark.id}"
                                    )
                                )
                            },
                            onInvalidUrl = {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        com.gdsc_vitbhopal.notegem.app.getString(R.string.invalid_url)
                                    )
                                }
                            }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(uiState.bookmarks) { bookmark ->
                        key(bookmark.id) {
                            BookmarkItem(
                                bookmark = bookmark,
                                onClick = {
                                    navController.navigate(
                                        Screen.BookmarkDetailScreen.route.replace(
                                            "{${Constants.BOOKMARK_ID_ARG}}",
                                            "${bookmark.id}"
                                        )
                                    )
                                },
                                onInvalidUrl = {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            com.gdsc_vitbhopal.notegem.app.getString(R.string.invalid_url)
                                        )
                                    }
                                },
                                modifier = Modifier.animateItemPlacement().height(220.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarksSettingsSection(
    order: Order,
    view: ItemView,
    onOrderChange: (Order) -> Unit,
    onViewChange: (ItemView) -> Unit
) {
    val orders = listOf(
        Order.DateModified(),
        Order.DateCreated(),
        Order.Alphabetical()
    )
    val orderTypes = listOf(
        OrderType.ASC(),
        OrderType.DESC()
    )
    val views = listOf(
        ItemView.LIST,
        ItemView.GRID
    )
    val bottomRoundedCorner = RoundedCornerShape(
        topStart = 30.dp,
        topEnd = 30.dp,
        bottomStart = 30.dp, // Adjust the radius as needed
        bottomEnd = 30.dp // Adjust the radius as needed
    )
    Column (modifier = Modifier.background(color = MaterialTheme.colors.surface, shape = bottomRoundedCorner)
        .padding(10.dp)){
        Text(
            text = stringResource(R.string.order_by),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp))
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
            views.forEach {
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
fun NoBookmarksMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_bookmarks_yet),
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.bookmark),
            contentDescription = stringResource(R.string.no_bookmarks_yet),
            alpha = 0.7f
        )
    }
}