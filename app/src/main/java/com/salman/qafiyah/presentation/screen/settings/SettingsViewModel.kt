package com.salman.qafiyah.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.qafiyah.domain.model.Settings
import com.salman.qafiyah.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2024.
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val mutableState = MutableStateFlow(SettingsState())
    val state = mutableState.asStateFlow()

    init {
        loadSettings()
        listenForSettingsChange()
    }

    fun setFontScale(scale: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableState.value = mutableState.value.copy(scale = scale)
            settingsRepository.updateFontScale(scale)
        }
    }

    private fun listenForSettingsChange() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.settingsFlow
                .onEach(::updateStateWithSettings)
                .launchIn(this)
        }
    }

    private fun loadSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getSettings().let(::updateStateWithSettings)
        }
    }

    private fun updateStateWithSettings(settings: Settings) {
        mutableState.value = mutableState.value.copy(scale = settings.fontScale)
    }
}