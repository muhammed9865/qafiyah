package com.salman.qafiyah.data.di

import com.salman.qafiyah.data.recognizer.SpeechRecognizerImpl
import com.salman.qafiyah.data.repository.SpeechRecognitionRepositoryImpl
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
val repositoryModule = module {

    factory<SpeechRecognizerImpl> {
        SpeechRecognizerImpl(androidContext())
    }

    single<SpeechRecognitionRepository> {
        SpeechRecognitionRepositoryImpl(get())
    }
}