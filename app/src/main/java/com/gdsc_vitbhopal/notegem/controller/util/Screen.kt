package com.gdsc_vitbhopal.notegem.controller.util

import com.gdsc_vitbhopal.notegem.util.Constants

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object DashboardScreen : Screen("dashboard_screen")
    object HomeScreen : Screen("home_screen")
    object SettingsScreen : Screen("settings_screen")
    object GrocerySearchScreen : Screen("grocery_search_screen")
    object TaskSearchScreen : Screen("task_search_screen")
    object TasksScreen : Screen("tasks_screen?${Constants.ADD_TASK_TILE_ARG}={${Constants.ADD_TASK_TILE_ARG}}")
    object BookmarkSearchScreen : Screen("bookmark_search_screen")
    object CalendarSearchScreen : Screen("calendar_search_screen")
    object TaskDetailScreen : Screen("task_detail_screen/{${Constants.TASK_ID_ARG}}")
    object NotesScreen : Screen("notes_screen")
    object NoteDetailsScreen : Screen("note_detail_screen/{${Constants.NOTE_ID_ARG}}")
    object NoteAddScreen : Screen("note_add_screen")
    object NoteSearchScreen : Screen("note_search_screen")
    object GroceryScreen : Screen("grocery_screen")
    object GroceryDetailScreen : Screen("grocery_detail_screen/{${Constants.GROCERY_ID_ARG}}")
    object GroceryAddScreen : Screen("grocery_add_screen")
    object GrocerySummaryScreen : Screen("grocery_summary_screen")
    object BookmarksScreen : Screen("bookmarks_screen")
    object BookmarkDetailScreen : Screen("bookmark_detail_screen/{${Constants.BOOKMARK_ID_ARG}}")
//    object BookmarkAddScreen : Screen("bookmark_add_screen")
    object CalendarScreen : Screen("calendar_screen")
}