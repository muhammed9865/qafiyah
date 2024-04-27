package com.salman.qafiyah

import android.app.Application
import com.salman.qafiyah.data.di.repositoryModule
import com.salman.qafiyah.presentation.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(viewModelsModule, repositoryModule)
        }
    }
}