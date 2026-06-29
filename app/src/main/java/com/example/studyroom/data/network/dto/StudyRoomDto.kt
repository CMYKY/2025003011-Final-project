package com.example.studyroom.data.network.dto

data class StudyRoomDto(
    val roomId: String,
    val name: String,
    val building: String,
    val floor: Int,
    val emptySeat: Int,
    val totalSeat: Int
)