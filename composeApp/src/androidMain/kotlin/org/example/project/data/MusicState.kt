package org.example.project.data

// MusicState.kt
data class MusicState(
    val isLoading: Boolean = false,
    val tracks: List<Song> = emptyList(),
    val error: String? = null
)