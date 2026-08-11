package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.data.remote.FdaApi
import com.kartikay.medlookup.data.remote.toDomainOrNull
import com.kartikay.medlookup.domain.model.Medicine

class MedicineRepository(
    private val api: FdaApi
) {

    suspend fun searchMedicines(query: String): Result<List<Medicine>> {
        return runCatching {
            val response = api.searchDrugs(
                search = "openfda.brand_name:\"$query\""
            )

            response.results.mapNotNull { drug ->
                drug.toDomainOrNull()
            }
        }
    }
}