package com.example.studyroom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyroom.data.entity.FavoriteRoom
import com.example.studyroom.data.repository.StudyRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(navController: NavController, repo: StudyRoomRepository) {
    val favList = repo.getFavoriteFlow().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("我的收藏自习室")
        Button(onClick = { navController.navigate("home") }) { Text("返回首页") }

        if (favList.value.isEmpty()) {
            Text("暂无收藏自习室")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(favList.value) { item: FavoriteRoom ->
                    Card(modifier = Modifier.padding(4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("自习室：${item.name}")
                            Text("楼栋 ${item.building} ${item.floor}楼")
                            Text("空位：${item.emptySeat}/${item.totalSeat}")
                            Button(onClick = {
                                scope.launch(Dispatchers.IO) { repo.removeFavorite(item) }
                            }) { Text("取消收藏") }
                        }
                    }
                }
            }
        }
    }
}