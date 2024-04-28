package com.salman.qafiyah.data.source

import android.content.Context
import android.content.SharedPreferences
import com.salman.qafiyah.domain.model.Settings
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2024.
 */
class LocalSettingsDataSource(
    private val context: Context
) {

    companion object {
        private const val SETTINGS_PREF = "settings_pref"
        private const val KEY_FONT_SCALE = "font_scale"
    }


    val settingsFlow: Flow<Settings>
        get() = callbackFlow {
            val sharedPreferences = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, s ->
                if (s == KEY_FONT_SCALE) {
                    trySend(Settings(sharedPreferences.getFloat(KEY_FONT_SCALE, 1.0f)))
                }
            }
            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
            awaitClose {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }

    fun getSettings(): Settings {
        val sharedPreferences = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)
        return Settings(sharedPreferences.getFloat(KEY_FONT_SCALE, 1.0f))
    }
    fun setFontScale(scale: Float) {
        val sharedPreferences = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)
        sharedPreferences.edit().putFloat(KEY_FONT_SCALE, scale).apply()
    }
}