package org.example.project.repository

import org.example.project.data.Song

class MusicRepository(
    private val localMusicRepository: LocalMusicRepository,
) {
    suspend fun loadMusic(): List<Song> {
        val localTracks = localMusicRepository.loadLocalMusic()
        return localTracks
    }
}