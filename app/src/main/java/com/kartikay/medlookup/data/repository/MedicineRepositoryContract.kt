package com.kartikay.medlookup.data.repository

interface MedicineRepositoryContract {

    suspend fun searchMedicines(
        query: String
    ): Result<MedicineSearchResult>
}