package com.gdsc_vitbhopal.notegem.data.repository

import com.gdsc_vitbhopal.notegem.data.local.dao.NoteDao
import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepositoryImpl (
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {
//    override suspend fun getAllNotes(): List<Note> {
//        return withContext(ioDispatcher) {
//            noteDao.getAllNotes()
//        }
//    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNote(id: Int): Note {
        return withContext(ioDispatcher) {
            noteDao.getNote(id)
        }
    }

    override suspend fun searchNotes(query: String): List<Note> {
        return withContext(ioDispatcher) {
            noteDao.getNotesByTitle(query)
        }
    }

    override suspend fun addNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.insertNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.updateNote(note)
        }
    }

    override suspend fun deleteNote(note: Note) {
        withContext(ioDispatcher) {
            noteDao.deleteNote(note)
        }
    }
}