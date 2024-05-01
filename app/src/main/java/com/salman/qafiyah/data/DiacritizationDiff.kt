package com.salman.qafiyah.data

import com.salman.qafiyah.domain.model.Diacritic
import com.salman.qafiyah.domain.model.DiacritizedLetter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
class DiacritizationDiff {

    private val diacritics = listOf(
        'َ',
        'ً',
        'ُ',
        'ٌ',
        'ِ',
        'ٍ',
        'ْ',
        'ّ',
        'ٓ',
        'ٰ',
        'ٔ',
        'ٖ',
        'ٗ',
        'ٚ',
        'ٛ',
        'ٜ',
        'ٝ',
        'ٞ',
        'ٟ'
    )

    suspend operator fun invoke(original: String, diacritized: String): List<DiacritizedLetter> =
        withContext(Dispatchers.Default) {
            val differences = mutableListOf<DiacritizedLetter>()
            val maxLength = maxOf(original.length, diacritized.length)
            var originalIndex = 0
            var diacriticIndex = 0

            for(i in 0 until maxLength) {
                val originalChar = original.getOrNull(originalIndex)
                val diacriticChar = diacritized.getOrNull(diacriticIndex)

                if (originalChar == diacriticChar) {
                    println("$i - Equals")
                    differences.add(DiacritizedLetter(originalChar!!, Diacritic.Skipped))
                    originalIndex++
                    diacriticIndex++
                    continue
                }

                if (originalChar !in diacritics && diacriticChar in diacritics) {
                    println("$i - $originalChar Original not diacritic, $diacriticChar Diacritized is diacritic")
                    val status = if (differences.getOrNull(i - 1)?.diacriticStatus == Diacritic.Corrected) Diacritic.Corrected else Diacritic.Added
                    differences.add(DiacritizedLetter(diacriticChar!!, status))
                    diacriticIndex++
                    continue
                }

                if (originalChar in diacritics && diacriticChar in diacritics) {
                    println("$i - $originalChar - $diacriticChar Both are diacritics")
                    differences.add(DiacritizedLetter(diacriticChar!!, Diacritic.Corrected))
                    originalIndex++
                    diacriticIndex++
                }
            }

            return@withContext differences
        }
}

fun main() = runBlocking {
    val originalText = "السُلام عليكم"
    val diacritizedText = "السَّلَامُ عَلَيْكُمُّْ"
    val diacritizationDiff = DiacritizationDiff()
    val diff = diacritizationDiff(originalText, diacritizedText)
    diff.forEach(::println)
    // print the diff with colors
    diff.forEach {
        when (it.diacriticStatus) {
            Diacritic.Added -> print("\u001B[33m${it.letter}\u001B[0m")
            Diacritic.Corrected -> print("\u001B[35m${it.letter}\u001B[0m")
            Diacritic.Skipped -> print(it.letter)
        }
    }
}