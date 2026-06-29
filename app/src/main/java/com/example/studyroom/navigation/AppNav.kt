package com.example.studyroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.studyroom.data.repository.StudyRoomRepository
import com.example.studyroom.data.datastore.UserPrefRepository
import com.example.studyroom.ui.screens.FavoriteScreen
import com.example.studyroom.ui.screens.HomeScreen
import com.example.studyroom.ui.screens.ReservationScreen
import com.example.studyroom.ui.screens.RoomDetailScreen
import com.example.studyroom.ui.screens.SettingScreen

@Composable
fun AppNav(
    navController: NavHostController,
    roomRepo: StudyRoomRepository,
    prefRepo: UserPrefRepository
) {
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(navController, roomRepo)
        }
        composable(route = "reservation") {
            ReservationScreen(navController, roomRepo)
        }
        composable(route = "setting") {
            SettingScreen(navController, roomRepo)
        }
        composable(
            route = "room_detail/{roomId}",
            arguments = listOf(navArgument("roomId") {})
        ) { backStackEntry: androidx.navigation.NavBackStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            RoomDetailScreen(navController, roomId, roomRepo)
        }
        composable(route = "favorite") {
            FavoriteScreen(navController, roomRepo)
        }
    }
}