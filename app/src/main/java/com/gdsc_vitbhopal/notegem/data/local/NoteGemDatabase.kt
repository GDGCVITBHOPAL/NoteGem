package com.gdsc_vitbhopal.notegem.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gdsc_vitbhopal.notegem.data.local.converters.DBConverters
import com.gdsc_vitbhopal.notegem.data.local.dao.AlarmDao
import com.gdsc_vitbhopal.notegem.data.local.dao.BookmarkDao
import com.gdsc_vitbhopal.notegem.data.local.dao.GroceryDao
import com.gdsc_vitbhopal.notegem.data.local.dao.NoteDao
import com.gdsc_vitbhopal.notegem.data.local.dao.TaskDao
import com.gdsc_vitbhopal.notegem.domain.model.Alarm
import com.gdsc_vitbhopal.notegem.domain.model.Bookmark
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.model.Task

@Database(
    entities = [Note::class, Task::class, GroceryEntry::class, Bookmark::class, Alarm::class],
    version = 1
)
@TypeConverters(DBConverters::class)
abstract class NoteGemDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun diaryDao(): GroceryDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun alarmDao(): AlarmDao

    companion object {
        const val DATABASE_NAME = "note_gem_db"
    }
}