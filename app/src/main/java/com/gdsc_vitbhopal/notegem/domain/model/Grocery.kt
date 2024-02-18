package com.gdsc_vitbhopal.notegem.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groceries")
data class Grocery(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)