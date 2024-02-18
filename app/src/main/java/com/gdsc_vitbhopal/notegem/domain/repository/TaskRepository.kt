package com.gdsc_vitbhopal.notegem.domain.repository

import com.gdsc_vitbhopal.notegem.domain.model.Task

interface TaskRepository{
    suspend fun getAllTasks(): List<Task>

    suspend fun getTaskById(id: Int): Task

    suspend fun searchTasks(title: String): List<Task>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun completeTask(id: Int, completed: Boolean)

    suspend fun deleteTask(task: Task)

}