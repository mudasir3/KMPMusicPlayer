package org.example.project.ui

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.Song

@Composable
fun DetailScreen(songId: Int, songs: List<Song>, onPrevious: () -> Unit, onNext: () -> Unit) {
    val song = songs.firstOrNull { it.id == songId } ?: return

    // Use MediaPlayer to play the song

    // Use MediaPlayer to play the song
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer() }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) } // Current position in seconds

    DisposableEffect(Unit) {
        mediaPlayer.setDataSource(song.filePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
        onDispose {
            mediaPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = mediaPlayer.currentPosition.toFloat() / 1000 // Convert milliseconds to seconds
            kotlinx.coroutines.delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = song.title, fontSize = 24.sp)
        Text(text = song.artist, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // Seekbar
        val duration = (mediaPlayer.duration / 1000).toFloat() // Total duration in seconds
        Slider(
            value = currentPosition,
            onValueChange = { newPosition ->
                currentPosition = newPosition
                mediaPlayer.seekTo((newPosition * 1000).toInt()) // Convert seconds to milliseconds
            },
            valueRange = 0f..duration,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Current time
            Text(text = formatTime(currentPosition))

            // Total duration
            Text(text = formatTime(duration))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Control buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPrevious) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Previous")
            }

            IconButton(onClick = {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    isPlaying = false
                } else {
                    mediaPlayer.start()
                    isPlaying = true
                }
            }) {
                Icon(
                    if (isPlaying) Icons.Filled.Place else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }

            IconButton(onClick = onNext) {
                Icon(Icons.Filled.Done, contentDescription = "Next")
            }
        }
    }
}

fun formatTime(time: Float): String {
    val minutes = (time / 60).toInt()
    val seconds = (time % 60).toInt()
    return String.format("%02d:%02d", minutes, seconds)
}