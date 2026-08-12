package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.data.remote.FdaApi
import com.kartikay.medlookup.data.remote.toDomainOrNull
import com.kartikay.medlookup.domain.model.Medicine
import retrofit2.HttpException

class MedicineRepository(
    private val api: FdaApi
) {

    suspend fun searchMedicines(
        query: String
    ): Result<List<Medicine>> {
        return try {
            val searchQuery = "openfda.brand_name:${query}*"
            println("FDA SEARCH QUERY: $searchQuery")

            val response = api.searchDrugs(
                search = searchQuery
            )

            println("FDA RESPONSE CODE: ${response.code()}")

            when {
                response.code() == 404 -> {
                    Result.success(emptyList())
                }

                response.isSuccessful -> {
                    val medicines = response.body()
                        ?.results
                        ?.mapNotNull { it.toDomainOrNull() }
                        ?: emptyList()

                    Result.success(medicines)
                }

                else -> {
                    Result.failure(
                        HttpException(response)
                    )
                }
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}