package com.salman.qafiyah.data.recognizer

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.salman.qafiyah.domain.model.SpeechRecognitionState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class SpeechRecognizerImpl(
    private val packageName: String,
    private val speechRecognizer: SpeechRecognizer
) {

    companion object {
        private const val TAG = "SpeechRecognizerImpl"
    }

    /**
     * Channel to send the recognition state [SpeechRecognitionState]
     * to the caller when the recognition is done.
     */
    val recognitionStatus = Channel<SpeechRecognitionState>()


    suspend fun startSpeechRecognition(): Result<Unit> {
        val recognitionIntent = getRecognitionIntent()
        val recognitionListener = getRecognitionListener()
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
            Result.success(speechRecognizer.cancel())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun getRecognitionIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        }
    }

    private suspend fun getRecognitionListener(): RecognitionListener = coroutineScope {
        return@coroutineScope object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                recognitionStatus.trySend(SpeechRecognitionState.Started)
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
                recognitionStatus.trySend(SpeechRecognitionState.Error("Error code: $p0"))
            }

            override fun onResults(p0: Bundle?) {
                val results = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                results?.firstOrNull()?.let {
                    recognitionStatus.trySend(SpeechRecognitionState.Recognized(it))
                } ?: recognitionStatus.trySend(SpeechRecognitionState.Error("No results found"))
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