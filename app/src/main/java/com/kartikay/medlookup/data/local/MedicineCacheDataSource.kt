package com.kartikay.medlookup.data.local

import com.kartikay.medlookup.domain.model.Medicine

interface MedicineCacheDataSource {

    suspend fun save(
        query: String,
        medicines: List<Medicine>
    )

    suspend fun load(
        query: String
    ): List<Medicine>?
}