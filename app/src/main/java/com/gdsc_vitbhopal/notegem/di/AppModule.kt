package com.gdsc_vitbhopal.notegem.di

import android.content.Context
import androidx.room.Room
import com.gdsc_vitbhopal.notegem.app.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.gdsc_vitbhopal.notegem.data.local.NoteGemDatabase
import com.gdsc_vitbhopal.notegem.data.local.dao.AlarmDao
import com.gdsc_vitbhopal.notegem.data.local.dao.BookmarkDao
import com.gdsc_vitbhopal.notegem.data.local.dao.GroceryDao
import com.gdsc_vitbhopal.notegem.data.local.dao.NoteDao
import com.gdsc_vitbhopal.notegem.data.local.dao.TaskDao
import com.gdsc_vitbhopal.notegem.data.repository.AlarmRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.BookmarkRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.CalendarRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.GroceryRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.NoteRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.SettingsRepositoryImpl
import com.gdsc_vitbhopal.notegem.data.repository.TaskRepositoryImpl
import com.gdsc_vitbhopal.notegem.domain.repository.AlarmRepository
import com.gdsc_vitbhopal.notegem.domain.repository.BookmarkRepository
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import com.gdsc_vitbhopal.notegem.domain.repository.SettingsRepository
import com.gdsc_vitbhopal.notegem.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyBrainDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NoteGemDatabase::class.java,
        NoteGemDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNoteDao(noteGemDatabase: NoteGemDatabase) = noteGemDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)

    @Singleton
    @Provides
    fun provideTaskDao(noteGemDatabase: NoteGemDatabase) = noteGemDatabase.taskDao()

    @Singleton
    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository = TaskRepositoryImpl(taskDao)

    @Singleton
    @Provides
    fun provideBookmarkDao(noteGemDatabase: NoteGemDatabase) = noteGemDatabase.bookmarkDao()

    @Singleton
    @Provides
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository = BookmarkRepositoryImpl(bookmarkDao)

    @Singleton
    @Provides
    fun provideGroceryDao(noteGemDatabase: NoteGemDatabase) = noteGemDatabase.diaryDao()

    @Singleton
    @Provides
    fun provideGroceryRepository(diaryDao: GroceryDao): GroceryRepository = GroceryRepositoryImpl(diaryDao)

    @Singleton
    @Provides
    fun provideCalendarRepository(@ApplicationContext context: Context): CalendarRepository = CalendarRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideAlarmDao(noteGemDatabase: NoteGemDatabase) = noteGemDatabase.alarmDao()

    @Singleton
    @Provides
    fun provideAlarmRepository(alarmDao: AlarmDao): AlarmRepository = AlarmRepositoryImpl(alarmDao)

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository = SettingsRepositoryImpl(context.dataStore)
}