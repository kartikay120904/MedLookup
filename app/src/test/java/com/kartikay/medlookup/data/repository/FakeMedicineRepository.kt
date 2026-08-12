package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.domain.model.Medicine

class FakeMedicineRepository : MedicineRepositoryContract {

    var result: Result<MedicineSearchResult> =
        Result.success(
            MedicineSearchResult(
                medicines = emptyList(),
                fromCache = false
            )
        )

    var lastQuery: String? = null

    override suspend fun searchMedicines(
        query: String
    ): Result<MedicineSearchResult> {
        lastQuery = query
        return result
    }
}