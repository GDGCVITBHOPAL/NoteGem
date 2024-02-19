package com.gdsc_vitbhopal.notegem.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_date")
    val createdDate: Long,
    @ColumnInfo(name = "updated_date")
    val updatedDate: Long,
    val color: Long,
    val pinned: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)