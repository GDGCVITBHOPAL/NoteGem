package com.gdsc_vitbhopal.notegem.controller.bookmarks

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.app.getString
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkSearchScreen(
    navController: NavHostController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            var query by rememberSaveable {
                mutableStateOf("")
            }
            LaunchedEffect(query){viewModel.onEvent(BookmarkEvent.SearchBookmarks(query))}
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(true){focusRequester.requestFocus()}
            OutlinedTextField(
                value = query,
                onValueChange = {query = it},
                label = { Text(stringResource(R.string.search_bookmarks)) },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester)
            )
            if (state.bookmarksView == ItemView.LIST){
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(state.searchBookmarks, key = {it.id}) { bookmark ->
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
                                        getString(R.string.invalid_url)
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
                ){
                    items(state.searchBookmarks){ bookmark ->
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
                                            getString(R.string.invalid_url)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}