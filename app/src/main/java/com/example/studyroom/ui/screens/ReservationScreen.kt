package com.example.studyroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studyroom.data.entity.Reservation
import com.example.studyroom.data.repository.StudyRoomRepository
import kotlinx.coroutines.launch

// 1. 补全导入：collectAsStateWithLifecycle、协程scope
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReservationScreen(
    navController: NavHostController,
    repo: StudyRoomRepository
) {
    // 协程scope，用于调用suspend删除方法
    val scope = rememberCoroutineScope()

    // 明确指定泛型，解决类型推断失败
    val reserveList = repo.getAllReservationFlow()
        .collectAsStateWithLifecycle(initialValue = emptyList<Reservation>())
        .value

    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Column {
            Button(onClick = { navController.popBackStack() }) {
                Text("返回首页")
            }
            if (reserveList.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("暂无预约记录，去首页预约自习室")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(reserveList) { reserve ->
                        ReserveCard(reserve) {
                            // 2. suspend函数必须放进launch协程，修复 suspend 调用报错
                            scope.launch {
                                repo.delReservation(reserve)
                            }
                        }
                    }
                }
            }
        }
    }
}

// 参数明确为 Reservation，修复Int类型不匹配
@Composable
fun ReserveCard(item: Reservation, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text("自习室：${item.roomName}")
            Text("日期：${item.date} ${item.startTime}-${item.endTime}")
            Button(onClick = onDelete) {
                Text("取消预约")
            }
        }
    }
}