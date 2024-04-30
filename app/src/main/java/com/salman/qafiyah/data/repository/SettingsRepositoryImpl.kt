package com.salman.qafiyah.data.repository

import com.salman.qafiyah.data.source.LocalSettingsDataSource
import com.salman.qafiyah.domain.model.Settings
import com.salman.qafiyah.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2024.
 */
class SettingsRepositoryImpl(
    private val settingsDataSource: LocalSettingsDataSource,
) : SettingsRepository {
    override val settingsFlow: Flow<Settings>
        get() = settingsDataSource.settingsFlow

    override suspend fun updateFontScale(scale: Float) {
        settingsDataSource.setFontScale(scale)
    }

    override suspend fun getSettings(): Settings {
        return settingsDataSource.getSettings()
    }
}