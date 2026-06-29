package com.example.studyroom.viewmodel

import com.example.studyroom.data.network.dto.StudyRoomDto

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val roomList: List<StudyRoomDto>) : HomeUiState
    data class Error(val errorMsg: String) : HomeUiState
    object Empty : HomeUiState
}