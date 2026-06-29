package com.example.studyroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyroom.data.network.dto.StudyRoomDto
import com.example.studyroom.data.repository.StudyRoomRepository
@Composable
fun HomeScreen(
    navController: NavController,
    repo: StudyRoomRepository
) {
    val roomList = repo.getRoomListFlow()
        .collectAsStateWithLifecycle(initialValue = emptyList<StudyRoomDto>())
        .value

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 自习室列表区域
        Column(modifier = Modifier.weight(1f)) {
            if (roomList.isEmpty()) {
                Text("暂无自习室数据，正在加载...")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(roomList) { room ->
                        RoomCardItem(room) {
                            navController.navigate("room_detail/${room.roomId}")
                        }
                    }
                }
            }
        }

        // 底部功能按钮：我的收藏、设置
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("favorite") }
            ) {
                Text("我的收藏")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("setting") }
            ) {
                Text("设置")
            }
        }
    }
}

@Composable
fun RoomCardItem(item: StudyRoomDto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "自习室：${item.name}")
            Text(text = "楼栋 ${item.building} ${item.floor}楼")
            Text(text = "空位：${item.emptySeat}/${item.totalSeat}")
        }
    }
}