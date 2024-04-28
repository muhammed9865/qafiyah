package com.salman.qafiyah.domain.repository

import com.salman.qafiyah.domain.model.Settings
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2024.
 */
interface SettingsRepository {

    val settingsFlow: Flow<Settings>
    suspend fun updateFontScale(scale: Float)
}