package com.example.studyroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.studyroom.data.database.AppDatabase
import com.example.studyroom.data.datastore.UserPrefRepository
import com.example.studyroom.data.datasource.ThemeDataStore
import com.example.studyroom.data.network.ApiService
import com.example.studyroom.data.network.NetClient
import com.example.studyroom.data.network.NetworkDataSource
import com.example.studyroom.data.repository.StudyRoomRepository
import com.example.studyroom.navigation.AppNav
import com.example.studyroom.ui.theme.StudyRoomTheme

class MainActivity : ComponentActivity() {
    private val api: ApiService = NetClient.apiService
    private val networkSource = NetworkDataSource(api)

    private lateinit var roomRepo: StudyRoomRepository
    private lateinit var prefRepo: UserPrefRepository
    // 新增主题DataStore实例
    private lateinit var themeDataStore: ThemeDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化主题存储
        themeDataStore = ThemeDataStore(this)
        // Room 数据库初始化
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "study_room_db"
        ).build()

        // 修复：补全第四个参数 themeDataStore
        roomRepo = StudyRoomRepository(
            networkDataSource = networkSource,
            reservationDao = db.reservationDao(),
            favoriteDao = db.favoriteDao(),
            themeDataStore = themeDataStore
        )
        prefRepo = UserPrefRepository(this)

        setContent {
            // 读取DataStore持久化深色模式
            val darkModeState = roomRepo.darkModeFlow.collectAsStateWithLifecycle(initialValue = false)
            // 修复明暗配色导入调用
            val appLightScheme = lightColorScheme()
            val appDarkScheme = darkColorScheme()
            val colorScheme = if (darkModeState.value) appDarkScheme else appLightScheme

            StudyRoomTheme {
                MaterialTheme(colorScheme = colorScheme) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navCtrl = rememberNavController()
                        AppNav(
                            navController = navCtrl,
                            roomRepo = roomRepo,
                            prefRepo = prefRepo
                        )
                    }
                }
            }
        }
    }
}