package com.salman.qafiyah.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.qafiyah.domain.model.SpeechRecognitionState
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class HomeViewModel(
    private val speechRecognitionRepository: SpeechRecognitionRepository
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

    private fun listenForSpeechRecognitionStateChange() {
        speechRecognitionRepository.recognitionStateFlow
            .onEach { state ->
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
                }
            }.launchIn(viewModelScope)
    }

}