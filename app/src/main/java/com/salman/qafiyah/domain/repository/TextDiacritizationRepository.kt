package com.salman.qafiyah.domain.repository

import com.salman.qafiyah.domain.model.DiacritizedLetter

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
interface TextDiacritizationRepository {

    suspend fun diacritizeText(text: String): String
    suspend fun diacritizeTextWithStatus(text: String): List<DiacritizedLetter>
}