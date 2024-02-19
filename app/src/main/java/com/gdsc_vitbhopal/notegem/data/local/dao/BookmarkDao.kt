package com.gdsc_vitbhopal.notegem.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gdsc_vitbhopal.notegem.domain.model.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    suspend fun getAll(): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getBookmark(id: Int): Bookmark

    @Query("SELECT * FROM bookmarks WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    suspend fun getBookmarksByTitle(query: String): List<Bookmark>


    @Insert
    suspend fun insertBookmark(bookmark: Bookmark)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)
}