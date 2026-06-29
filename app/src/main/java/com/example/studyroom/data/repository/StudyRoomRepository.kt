package com.example.studyroom.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.studyroom.data.dao.FavoriteDao
import com.example.studyroom.data.dao.ReservationDao
import com.example.studyroom.data.datasource.ThemeDataStore
import com.example.studyroom.data.network.NetworkDataSource
import com.example.studyroom.data.network.dto.StudyRoomDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress(names = ["UNUSED"])
class StudyRoomRepository(
    private val networkDataSource: NetworkDataSource,
    private val reservationDao: ReservationDao,
    private val favoriteDao: FavoriteDao,
    private val themeDataStore: ThemeDataStore
) {
    private val roomMemoryList = mutableStateListOf(
        StudyRoomDto("r1", "一号自习室", "A栋", 2, 12, 30),
        StudyRoomDto("r2", "三号自习室", "C栋", 5, 6, 28)
    )

    fun getRoomListFlow(): Flow<List<StudyRoomDto>> = flow {
        try {
            delay(500)
            emit(networkDataSource.fetchStudyRooms())
        } catch (_: Exception) {
            emit(roomMemoryList)
        }
    }

    suspend fun getRemoteStudyRooms(): List<StudyRoomDto> {
        return try {
            networkDataSource.fetchStudyRooms()
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun reduceSeat(roomId: String) {
        val target = roomMemoryList.find { it.roomId == roomId } ?: return
        val newItem = target.copy(emptySeat = target.emptySeat - 1)
        roomMemoryList.remove(target)
        roomMemoryList.add(newItem)
    }

    // 预约
    fun getAllReservationFlow() = reservationDao.getAllReservations()
    fun searchReservation(keyword: String) = reservationDao.searchReservation(keyword)
    suspend fun insertReservation(reservation: com.example.studyroom.data.entity.Reservation) {
        try { reservationDao.addReservation(reservation) } catch (e: Exception) { e.printStackTrace() }
    }
    suspend fun delReservation(reservation: com.example.studyroom.data.entity.Reservation) = reservationDao.deleteReservation(reservation)

    // 收藏
    fun getFavoriteFlow() = favoriteDao.getAllFavorite()
    fun roomIsFavorite(roomId: String) = favoriteDao.isFavorite(roomId)
    suspend fun addFavorite(fav: com.example.studyroom.data.entity.FavoriteRoom) = favoriteDao.addFavorite(fav)
    suspend fun removeFavorite(fav: com.example.studyroom.data.entity.FavoriteRoom) = favoriteDao.removeFavorite(fav)

    // 主题DataStore
    val darkModeFlow: Flow<Boolean> = themeDataStore.isDarkModeFlow
    suspend fun saveDarkMode(enable: Boolean) = themeDataStore.setDarkMode(enable)
}