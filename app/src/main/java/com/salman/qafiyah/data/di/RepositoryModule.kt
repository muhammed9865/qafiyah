package com.salman.qafiyah.data.di

import com.salman.qafiyah.data.recognizer.SpeechRecognizerImpl
import com.salman.qafiyah.data.repository.ClipboardRepositoryImpl
import com.salman.qafiyah.data.repository.SettingsRepositoryImpl
import com.salman.qafiyah.data.repository.SpeechRecognitionRepositoryImpl
import com.salman.qafiyah.data.repository.TextDiacritizationRepositoryImpl
import com.salman.qafiyah.data.source.DiacritizationRemoteSource
import com.salman.qafiyah.data.source.LocalSettingsDataSource
import com.salman.qafiyah.domain.repository.ClipboardRepository
import com.salman.qafiyah.domain.repository.SettingsRepository
import com.salman.qafiyah.domain.repository.SpeechRecognitionRepository
import com.salman.qafiyah.domain.repository.TextDiacritizationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
val repositoryModule = module {

    factory<SpeechRecognizerImpl> {
        SpeechRecognizerImpl(androidContext())
    }

    single<LocalSettingsDataSource> {
        LocalSettingsDataSource(androidContext())
    }

    single<SpeechRecognitionRepository> {
        SpeechRecognitionRepositoryImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<ClipboardRepository> {
        ClipboardRepositoryImpl(androidContext())
    }

    single<TextDiacritizationRepository> {
        val diacritizationSource = DiacritizationRemoteSource()
        TextDiacritizationRepositoryImpl(diacritizationSource)
    }
}