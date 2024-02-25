package com.gdsc_vitbhopal.notegem.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gdsc_vitbhopal.notegem.domain.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :title || '%'")
    suspend fun getTasksByTitle(title: String): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :id")
    suspend fun updateCompleted(id: Int, completed: Boolean)
}