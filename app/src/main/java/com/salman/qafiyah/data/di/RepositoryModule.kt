package com.salman.qafiyah.data.di

import android.speech.SpeechRecognizer
import com.salman.qafiyah.data.recognizer.SpeechRecognizerImpl
import com.salman.qafiyah.data.repository.SpeechRecognitionRepositoryImpl
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
val repositoryModule = module {
    factory<SpeechRecognizer> {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(androidContext())
        registerCallback(object : ScopeCallback {
            override fun onScopeClose(scope: Scope) {
                recognizer.destroy()
            }
        })
        recognizer
    }

    factory<SpeechRecognizerImpl> {
        SpeechRecognizerImpl(androidContext().packageName, get())
    }

    single<SpeechRecognitionRepository> {
        SpeechRecognitionRepositoryImpl(get())
    }
}