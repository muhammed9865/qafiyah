package com.salman.qafiyah.data.repository

import com.salman.qafiyah.data.DiacritizationDiff
import com.salman.qafiyah.data.source.DiacritizationRemoteSource
import com.salman.qafiyah.domain.model.DiacritizedLetter
import com.salman.qafiyah.domain.repository.TextDiacritizationRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
class TextDiacritizationRepositoryImpl(
    private val remoteSource: DiacritizationRemoteSource,
) : TextDiacritizationRepository {
    override suspend fun diacritizeText(text: String): String {
        return runCatching { remoteSource.diacritizeText(text) }.getOrDefault(text)
    }

    override suspend fun diacritizeTextWithStatus(text: String): List<DiacritizedLetter> {

        val diacritizedText = diacritizeText(text)

        return DiacritizationDiff()(text, diacritizedText)
    }
}