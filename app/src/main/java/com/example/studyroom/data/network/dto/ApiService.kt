package com.example.studyroom.data.network

import com.example.studyroom.data.network.dto.StudyRoomDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("studyRoom/list")
    suspend fun getStudyRoomList(): Response<List<StudyRoomDto>>
}