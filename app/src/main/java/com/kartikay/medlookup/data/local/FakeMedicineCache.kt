package com.kartikay.medlookup.data.local

import com.kartikay.medlookup.domain.model.Medicine

class FakeMedicineCache : MedicineCacheDataSource {

    private val data = mutableMapOf<String, List<Medicine>>()

    override suspend fun save(
        query: String,
        medicines: List<Medicine>
    ) {
        data[query.lowercase()] = medicines
    }

    override suspend fun load(
        query: String
    ): List<Medicine>? {
        return data[query.lowercase()]
    }
}