package com.salman.qafiyah.data.recognizer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.salman.qafiyah.domain.model.SpeechRecognitionState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class SpeechRecognizerImpl(
    private val context: Context,
) {

    companion object {
        private const val TAG = "SpeechRecognizerImpl"
    }

    /**
     * StateFlow to send the recognition state [SpeechRecognitionState]
     * to the caller when the recognition is done.
     */
    val recognitionStatus =
        MutableStateFlow<SpeechRecognitionState>(SpeechRecognitionState.Idle).apply {
            onStart { Log.d(TAG, "RecognitionState: Starting") }
            onCompletion { Log.d(TAG, "RecognitionState: Closing") }
        }

    private var speechRecognizer: SpeechRecognizer? = null


    suspend fun startSpeechRecognition(): Result<Unit> {
        val recognitionIntent = getRecognitionIntent()
        val recognitionListener = getRecognitionListener()
        val speechRecognizer = getRecognizer()
        return try {
            speechRecognizer.setRecognitionListener(recognitionListener)
            speechRecognizer.startListening(recognitionIntent)
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    fun stopSpeechRecognition(): Result<Unit> {
        return try {
            speechRecognizer?.cancel()
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun getRecognizer() =
        speechRecognizer ?: SpeechRecognizer.createSpeechRecognizer(context).also {
            speechRecognizer = it
        }

    private fun getRecognitionIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf<String>())
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG")
        }
    }

    private suspend fun getRecognitionListener(): RecognitionListener = coroutineScope {
        return@coroutineScope object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                recognitionStatus.update { SpeechRecognitionState.Started }
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech")
            }

            override fun onRmsChanged(p0: Float) {
                Log.d(TAG, "onRmsChanged: $p0")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Log.d(TAG, "onBufferReceived: $p0")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech")
            }

            override fun onError(p0: Int) {
                Log.d(TAG, "onError: $p0")
                recognitionStatus.update { SpeechRecognitionState.Error("Error code: $p0") }
            }

            override fun onResults(p0: Bundle?) {
                val results = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                results?.firstOrNull()?.let { recognizedText ->
                    recognitionStatus.update { SpeechRecognitionState.Recognized(recognizedText) }
                } ?: recognitionStatus.update { SpeechRecognitionState.Error("No results found") }

                speechRecognizer?.destroy()
                speechRecognizer = null
            }

            override fun onPartialResults(p0: Bundle?) {
                // No implementation needed
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                // No implementation needed
            }
        }
    }
}