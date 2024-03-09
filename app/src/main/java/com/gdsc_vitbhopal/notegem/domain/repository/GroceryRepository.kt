package com.gdsc_vitbhopal.notegem.domain.repository

import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import kotlinx.coroutines.flow.Flow

interface GroceryRepository {
    fun getAllEntries(): Flow<List<GroceryEntry>>

    suspend fun getEntry(id: Int): GroceryEntry

    suspend fun searchEntries(title: String): List<GroceryEntry>

    suspend fun addEntry(diary: GroceryEntry)

    suspend fun updateEntry(diary: GroceryEntry)

    suspend fun deleteEntry(diary: GroceryEntry)
}