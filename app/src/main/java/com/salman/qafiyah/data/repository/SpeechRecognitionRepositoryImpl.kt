package com.salman.qafiyah.data.repository

import com.salman.qafiyah.data.recognizer.SpeechRecognizerImpl
import com.salman.qafiyah.domain.model.SpeechRecognitionState
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class SpeechRecognitionRepositoryImpl(
    private val speechRecognizer: SpeechRecognizerImpl
) : SpeechRecognitionRepository {

    companion object {
        private const val TAG = "SpeechRecognitionRepositoryImpl"
    }

    override val recognitionStateFlow: Flow<SpeechRecognitionState>
        get() = speechRecognizer.recognitionStatus.consumeAsFlow()

    override suspend fun startSpeechRecognition(): Result<Unit> {
        return speechRecognizer.startSpeechRecognition()
    }

    override suspend fun stopSpeechRecognition(): Result<Unit> {
        return speechRecognizer.stopSpeechRecognition()
    }
}

