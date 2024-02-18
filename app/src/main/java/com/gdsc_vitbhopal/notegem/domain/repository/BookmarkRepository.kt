package com.gdsc_vitbhopal.notegem.domain.repository

import com.gdsc_vitbhopal.notegem.domain.model.Bookmark

interface BookmarkRepository{
    suspend fun getAllBookmarks(): List<Bookmark>

    suspend fun getBookmark(id: Int): Bookmark

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

    suspend fun updateBookmark(bookmark: Bookmark)
}