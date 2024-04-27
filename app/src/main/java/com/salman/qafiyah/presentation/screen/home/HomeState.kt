package com.salman.qafiyah.presentation.screen.home

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
data class HomeState(
    val textToBeDiacritized: String = "",
    val textAfterDiacritization: String = "",
    val isVoiceRecordingRunning: Boolean = false,
    val isDiacritizationRunning: Boolean = false,
    val error: String? = null
) {
    val showToBeDiacritizedHint: Boolean
        get() = textToBeDiacritized.isBlank() && !isVoiceRecordingRunning
}
