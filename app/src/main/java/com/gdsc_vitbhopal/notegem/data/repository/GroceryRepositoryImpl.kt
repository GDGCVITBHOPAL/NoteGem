package com.gdsc_vitbhopal.notegem.data.repository

import com.gdsc_vitbhopal.notegem.data.local.dao.GroceryDao
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroceryRepositoryImpl (
    private val groceryDao: GroceryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GroceryRepository {
    override suspend fun getAllEntries(): List<GroceryEntry> {
        return withContext(ioDispatcher) {
            groceryDao.getAllEntries()
        }
    }

    override suspend fun getEntry(id: Int): GroceryEntry {
        return withContext(ioDispatcher) {
            groceryDao.getEntry(id)
        }
    }

    override suspend fun searchEntries(title: String): List<GroceryEntry> {
        return withContext(ioDispatcher) {
            groceryDao.getEntriesByTitle(title)
        }
    }

    override suspend fun addEntry(diary: GroceryEntry) {
        withContext(ioDispatcher) {
            groceryDao.insertEntry(diary)
        }
    }

    override suspend fun updateEntry(diary: GroceryEntry) {
        withContext(ioDispatcher) {
            groceryDao.updateEntry(diary)
        }
    }

    override suspend fun deleteEntry(diary: GroceryEntry) {
        withContext(ioDispatcher) {
            groceryDao.deleteEntry(diary)
        }
    }
}