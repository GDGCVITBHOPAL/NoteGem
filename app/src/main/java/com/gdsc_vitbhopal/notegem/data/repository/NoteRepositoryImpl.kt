package com.gdsc_vitbhopal.notegem.data.repository

import com.gdsc_vitbhopal.notegem.data.local.dao.NoteDao
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NoteRepositoryImpl (
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository