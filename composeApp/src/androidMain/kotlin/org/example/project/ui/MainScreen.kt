package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.project.data.Song

@Composable
fun MainScreen(navController: NavController, songs: List<Song>) {
    LazyColumn {
        items(songs) { song ->
            SongItem(song = song, onClick = {
                navController.navigate("detail/${song.id}")
            })
        }
    }
}

@Composable
fun SongItem(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = song.title, style = MaterialTheme.typography.titleMedium)
        Text(text = song.artist, style = MaterialTheme.typography.bodyMedium)
    }
}

