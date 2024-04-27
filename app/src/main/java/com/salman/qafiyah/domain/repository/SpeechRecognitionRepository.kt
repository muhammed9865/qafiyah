package com.salman.qafiyah.domain.repository

import com.salman.qafiyah.domain.model.SpeechRecognitionState
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
interface SpeechRecognitionRepository {

    val recognitionStateFlow: Flow<SpeechRecognitionState>
    suspend fun startSpeechRecognition(): Result<Unit>
    suspend fun stopSpeechRecognition(): Result<Unit>
}