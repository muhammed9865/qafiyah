package com.salman.qafiyah.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
sealed class SpeechRecognitionState {

    data object Started : SpeechRecognitionState()
    data class Recognized(val text: String) : SpeechRecognitionState()
    data class Error(val message: String) : SpeechRecognitionState()
    data object Idle : SpeechRecognitionState()
}