package org.example.project.data

sealed class MusicEvent {
    object LoadMusic : MusicEvent()
    data class MusicLoaded(val tracks: List<Song>) : MusicEvent()
    data class MusicLoadError(val error: String) : MusicEvent()
}