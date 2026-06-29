package com.example.studyroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyroom.data.entity.FavoriteRoom
import com.example.studyroom.data.network.dto.StudyRoomDto
import com.example.studyroom.data.repository.StudyRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@Composable
fun RoomDetailScreen(
    navController: NavController,
    roomId: String,
    repo: StudyRoomRepository
) {
    val roomListState = repo.getRoomListFlow().collectAsState(initial = emptyList())
    val currentRoom = roomListState.value.find { it.roomId == roomId }
        ?: StudyRoomDto(roomId, "未知自习室", "", 0, 0, 0)

    val favCount = repo.roomIsFavorite(roomId).collectAsState(initial = 0)
    val isFav = favCount.value > 0
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "自习室名称：${currentRoom.name}")
        Text(text = "楼栋：${currentRoom.building} ${currentRoom.floor}层")
        Text(text = "剩余空位：${currentRoom.emptySeat}")

        // 收藏按钮
        Button(modifier = Modifier.padding(top = 12.dp), onClick = {
            scope.launch(Dispatchers.IO) {
                val fav = FavoriteRoom(
                    roomId = currentRoom.roomId,
                    name = currentRoom.name,
                    building = currentRoom.building,
                    floor = currentRoom.floor,
                    emptySeat = currentRoom.emptySeat,
                    totalSeat = currentRoom.totalSeat
                )
                if (isFav) repo.removeFavorite(fav) else repo.addFavorite(fav)
            }
        }) {
            Text(if (isFav) "取消收藏" else "收藏自习室")
        }

        // 预约按钮
        Button(modifier = Modifier.padding(top = 12.dp), onClick = {
            println("【日志】点击立即预约按钮")
            scope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        val date = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Calendar.getInstance().time)
                        val newId = UUID.randomUUID().toString()
                        val record = com.example.studyroom.data.entity.Reservation(
                            reserveId = newId,
                            roomId = currentRoom.roomId,
                            roomName = currentRoom.name,
                            date = date,
                            startTime = "08:00",
                            endTime = "22:00"
                        )
                        repo.insertReservation(record)
                        println("【日志】预约插入成功")
                    }
                    repo.reduceSeat(currentRoom.roomId)
                    println("【日志】空位-1")
                    navController.navigate("reservation")
                } catch (e: Exception) {
                    println("【日志】失败：${e.message}")
                    e.printStackTrace()
                }
            }
        }) {
            Text("立即预约")
        }

        Button(modifier = Modifier.padding(top = 12.dp), onClick = { navController.navigate("home") }) {
            Text("返回首页")
        }
    }
}