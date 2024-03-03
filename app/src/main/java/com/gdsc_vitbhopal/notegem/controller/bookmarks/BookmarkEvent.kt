package com.gdsc_vitbhopal.notegem.controller.bookmarks

import com.gdsc_vitbhopal.notegem.domain.model.Bookmark
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import com.gdsc_vitbhopal.notegem.util.settings.Order

sealed class BookmarkEvent {
    data class AddBookmark(val bookmark: Bookmark) : BookmarkEvent()
    data class GetBookmark(val bookmarkId: Int) : BookmarkEvent()
    data class SearchBookmarks(val query: String) : BookmarkEvent()
    data class UpdateOrder(val order: Order) : BookmarkEvent()
    data class UpdateView(val view: ItemView) : BookmarkEvent()
    data class UpdateBookmark(val bookmark: Bookmark) : BookmarkEvent()
    data class DeleteBookmark(val bookmark: Bookmark) : BookmarkEvent()
    object ErrorDisplayed: BookmarkEvent()
}