package org.example.project.di

import org.example.project.repository.LocalMusicRepository
import org.koin.dsl.module
import org.koin.core.module.Module
import org.example.project.repository.MusicRepository
import org.example.project.viewModels.MusicViewModel


val appModule = module {
    single { LocalMusicRepository(get()) }
    single { MusicRepository(get()) }
    single { MusicViewModel(get()) }
}