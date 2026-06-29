package com.example.studyroom.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.studyroom.data.entity.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservation")
    fun getAllReservations(): Flow<List<Reservation>>

    @Query("SELECT * FROM reservation WHERE roomName LIKE '%' || :keyword || '%'")
    fun searchReservation(keyword: String): Flow<List<Reservation>>

    @Insert
    suspend fun addReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)
}