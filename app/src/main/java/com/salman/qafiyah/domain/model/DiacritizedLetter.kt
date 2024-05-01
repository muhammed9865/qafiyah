package com.salman.qafiyah.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
data class DiacritizedLetter(
    val letter: Char,
    val diacriticStatus: Diacritic
)

enum class Diacritic {
    Added,
    Corrected,
    Skipped
}
