package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.project.viewModels.MusicViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MusicPlayerScreen(viewModel: MusicViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> Text(text = "Error: ${state.error}")
        else -> {
          App(state.tracks)
        }
    }
}


