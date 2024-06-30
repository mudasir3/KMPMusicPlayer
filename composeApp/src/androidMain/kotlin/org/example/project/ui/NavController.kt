package org.example.project.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.data.Song

@Composable
fun App(tracks: List<Song>) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController, tracks) }
        composable("detail/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")?.toInt() ?: return@composable
            DetailScreen(songId = songId, songs = tracks)
        }
    }
}