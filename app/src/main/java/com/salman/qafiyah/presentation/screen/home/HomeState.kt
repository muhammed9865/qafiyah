package com.salman.qafiyah.presentation.screen.home

import com.salman.qafiyah.domain.model.DiacritizedLetter

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
data class HomeState(
    val textToBeDiacritized: String = "",
    val textAfterDiacritization: List<DiacritizedLetter> = emptyList(),
    val isVoiceRecordingRunning: Boolean = false,
    val isDiacritizationRunning: Boolean = false,
    val fontScale: Float = 1f,
    val error: String? = null,
    val isTextCopied: Boolean = false,
) {
    val showToBeDiacritizedHint: Boolean
        get() = textToBeDiacritized.isBlank() && !isVoiceRecordingRunning
}
