package com.example.studyroom.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation")
data class Reservation(
    @PrimaryKey val reserveId: String,
    val roomId: String,
    val roomName: String,
    val date: String,
    val startTime: String,
    val endTime: String
)