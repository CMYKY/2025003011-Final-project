package com.example.studyroom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studyroom.data.dao.FavoriteDao
import com.example.studyroom.data.dao.ReservationDao
import com.example.studyroom.data.entity.FavoriteRoom
import com.example.studyroom.data.entity.Reservation

@Database(
    entities = [Reservation::class, FavoriteRoom::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
    abstract fun favoriteDao(): FavoriteDao
}