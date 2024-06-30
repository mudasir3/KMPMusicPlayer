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
            DetailScreen(
                songId = songId,
                songs = tracks,
                onPrevious = {
                    val previousIndex = (tracks.indexOfFirst { it.id == songId } - 1 + tracks.size) % tracks.size
                    navController.navigate("detail/${tracks[previousIndex].id}") {
                        popUpTo("detail") { inclusive = true }
                    }
                },
                onNext = {
                    val nextIndex = (tracks.indexOfFirst { it.id == songId } + 1) % tracks.size
                    navController.navigate("detail/${tracks[nextIndex].id}") {
                        popUpTo("detail") { inclusive = true }
                    }
                }
            )
        }
    }
}