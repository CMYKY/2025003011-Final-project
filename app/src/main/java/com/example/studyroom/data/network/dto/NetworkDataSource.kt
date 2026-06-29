package com.example.studyroom.data.network

import com.example.studyroom.data.network.dto.StudyRoomDto

class NetworkDataSource(
    private val apiService: ApiService
) {
    suspend fun fetchStudyRooms(): List<StudyRoomDto> {
        val response = apiService.getStudyRoomList()
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}