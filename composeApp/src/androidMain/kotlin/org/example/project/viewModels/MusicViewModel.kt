package org.example.project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.project.data.MusicState
import org.example.project.intent.MusicIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.repository.MusicRepository
import org.koin.core.component.KoinComponent

class MusicViewModel(
    private val repository: MusicRepository
) : ViewModel(), KoinComponent, MusicIntent {
    private val _state = MutableStateFlow(MusicState())
    val state: StateFlow<MusicState> = _state

    init {
        loadMusic()
    }

    override fun loadMusic() {
        viewModelScope.launch {
            _state.value = MusicState(isLoading = true)
            try {
                val tracks = repository.loadMusic()
                _state.value = MusicState(tracks = tracks)
            } catch (e: Exception) {
                _state.value = MusicState(error = e.message)
            }
        }
    }
}
