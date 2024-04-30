package com.salman.qafiyah.domain.repository

interface ClipboardRepository {

    suspend fun copyToClipboard(text: String): Result<Unit>
}