package com.example.studyroom.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyroom.data.repository.StudyRoomRepository
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, repo: StudyRoomRepository) {
    val darkMode = repo.darkModeFlow.collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        Text("应用设置")
        Button(onClick = { navController.navigate("home") }) { Text("返回首页") }

        Column(modifier = Modifier.padding(top = 20.dp)) {
            Text("深色模式")
            Switch(
                checked = darkMode.value,
                onCheckedChange = { scope.launch { repo.saveDarkMode(it) } }
            )
        }
    }
}