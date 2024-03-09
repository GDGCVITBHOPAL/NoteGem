package com.gdsc_vitbhopal.notegem.data.local.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Query("SELECT * FROM groceries")
    fun getAllEntries(): Flow<List<GroceryEntry>>

    @Query("SELECT * FROM groceries WHERE id = :id")
    suspend fun getEntry(id: Int): GroceryEntry

    @Query("SELECT * FROM groceries WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun getEntriesByTitle(query: String): List<GroceryEntry>

    @Insert
    suspend fun insertEntry(groceries: GroceryEntry)

    @Update
    suspend fun updateEntry(groceries: GroceryEntry)

    @Delete
    suspend fun deleteEntry(groceries: GroceryEntry)
}