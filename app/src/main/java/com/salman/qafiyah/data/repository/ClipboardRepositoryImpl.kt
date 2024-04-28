package com.salman.qafiyah.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import com.salman.qafiyah.R
import com.salman.qafiyah.domain.repository.ClipboardRepository

class ClipboardRepositoryImpl(
    private val context: Context
) : ClipboardRepository {
    override suspend fun copyToClipboard(text: String): Result<Unit> {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return try {
            clipboardManager.setPrimaryClip(
                ClipData.newPlainText(
                    context.getString(R.string.diacritized_text),
                    text
                )
            )
            Log.d("ClipboardRepository", "Text copied to clipboard")
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}