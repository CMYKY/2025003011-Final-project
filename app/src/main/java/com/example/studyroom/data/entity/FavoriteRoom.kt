package com.example.studyroom.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_room")
data class FavoriteRoom(
    @PrimaryKey val roomId: String,
    val name: String,
    val building: String,
    val floor: Int,
    val emptySeat: Int,
    val totalSeat: Int
)