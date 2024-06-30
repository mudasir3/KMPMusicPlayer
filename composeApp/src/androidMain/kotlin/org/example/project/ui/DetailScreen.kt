package org.example.project.ui

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.example.project.data.Song

@Composable
fun DetailScreen(songId: Int, songs: List<Song>) {
    val song = songs.firstOrNull { it.id == songId } ?: return

    // Use MediaPlayer to play the song
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer() }
    DisposableEffect(Unit) {
        mediaPlayer.setDataSource(song.filePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
        onDispose {
            mediaPlayer.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = song.title, style = MaterialTheme.typography.titleSmall)
        Text(text = song.artist, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }) {
            Text(text = if (mediaPlayer.isPlaying) "Pause" else "Play")
        }
    }
}