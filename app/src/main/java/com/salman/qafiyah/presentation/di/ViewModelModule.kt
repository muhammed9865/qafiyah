package com.salman.qafiyah.presentation.di

import com.salman.qafiyah.presentation.screen.home.HomeViewModel
import com.salman.qafiyah.presentation.screen.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
val viewModelsModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(get(), get(), get(), get())
    }
    viewModel<SettingsViewModel> {
        SettingsViewModel(get())
    }
}