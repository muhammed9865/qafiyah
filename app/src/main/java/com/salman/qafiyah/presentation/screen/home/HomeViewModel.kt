package com.salman.qafiyah.presentation.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.qafiyah.domain.model.SpeechRecognitionState
import com.salman.qafiyah.domain.repository.ClipboardRepository
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import com.salman.qafiyah.domain.repository.TextDiacritizationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class HomeViewModel(
    private val speechRecognitionRepository: SpeechRecognitionRepository,
    private val clipboardRepository: ClipboardRepository,
    private val diacritizationRepository: TextDiacritizationRepository,
) : ViewModel() {

    private val mutableState = MutableStateFlow(HomeState())
    val state = mutableState.asStateFlow()

    init {
        listenForSpeechRecognitionStateChange()
    }

    fun onTextToBeDiacritizedChanged(text: String) {
        mutableState.update { it.copy(textToBeDiacritized = text) }
    }

    fun startRecording() {
        viewModelScope.launch {
            speechRecognitionRepository.startSpeechRecognition()
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            speechRecognitionRepository.stopSpeechRecognition()
        }
    }

    fun diacritize() {
        viewModelScope.launch {
            mutableState.update {
                it.copy(isDiacritizationRunning = true)
            }

            val text = mutableState.value.textToBeDiacritized
            val diacritizedText = diacritizationRepository.diacritizeTextWithStatus(text)

            mutableState.update {
                it.copy(
                    textAfterDiacritization = diacritizedText,
                    isDiacritizationRunning = false
                )
            }
        }
    }

    fun copyTextToClipboard() {
        viewModelScope.launch {
            val text = mutableState.value.textAfterDiacritization
            val isCopied = clipboardRepository.copyToClipboard(text.map { it.letter }.joinToString("")).isSuccess
            mutableState.update { it.copy(isTextCopied = isCopied) }
            delay(100) // to show the copied state for a while
            mutableState.update { it.copy(isTextCopied = false) }
        }
    }

    private fun listenForSpeechRecognitionStateChange() {
        speechRecognitionRepository.recognitionStateFlow
            .onEach { state ->
                Log.d("HomeViewModel", "listenForSpeechRecognitionStateChange: $state")
                when (state) {
                    is SpeechRecognitionState.Started -> {
                        mutableState.update { it.copy(isVoiceRecordingRunning = true) }
                    }

                    is SpeechRecognitionState.Error -> {
                        mutableState.update {
                            it.copy(
                                isVoiceRecordingRunning = false,
                                error = state.message
                            )
                        }
                    }

                    is SpeechRecognitionState.Recognized -> {
                        mutableState.update {
                            it.copy(
                                error = null,
                                textToBeDiacritized = state.text,
                                isVoiceRecordingRunning = false
                            )
                        }
                    }

                    else -> { /* idle state */
                    }
                }
            }
            .onStart {
                Log.d(
                    "HomeViewModel",
                    "listenForSpeechRecognitionStateChange: started listening"
                )
            }
            .onCompletion {
                Log.d(
                    "HomeViewModel",
                    "listenForSpeechRecognitionStateChange: stopped listening"
                )
            }
            .launchIn(viewModelScope)
    }

}