package com.example.studyroom.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.studyroom.data.entity.FavoriteRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_room")
    fun getAllFavorite(): Flow<List<FavoriteRoom>>

    @Query("SELECT COUNT(*) FROM favorite_room WHERE roomId = :roomId")
    fun isFavorite(roomId: String): Flow<Int>

    @Insert
    suspend fun addFavorite(fav: FavoriteRoom)

    @Delete
    suspend fun removeFavorite(fav: FavoriteRoom)
}