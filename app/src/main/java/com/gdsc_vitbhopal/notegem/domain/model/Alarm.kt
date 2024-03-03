package com.gdsc_vitbhopal.notegem.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val time: Long,
)