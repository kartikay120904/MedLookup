package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.data.local.MedicineCacheDataSource
import com.kartikay.medlookup.data.remote.FdaApi
import com.kartikay.medlookup.data.remote.toDomainOrNull
import retrofit2.HttpException

class MedicineRepository(
    private val api: FdaApi,
    private val cache: MedicineCacheDataSource
) : MedicineRepositoryContract {

    override suspend fun searchMedicines(
        query: String
    ): Result<MedicineSearchResult> {
        return try {
            val searchQuery = "openfda.brand_name:${query}*"

            val response = api.searchDrugs(
                search = searchQuery
            )

            when {
                response.code() == 404 -> {
                    Result.success(
                        MedicineSearchResult(
                            medicines = emptyList(),
                            fromCache = false
                        )
                    )
                }

                response.isSuccessful -> {
                    val medicines = response.body()
                        ?.results
                        ?.mapNotNull { it.toDomainOrNull() }
                        ?: emptyList()

                    if (medicines.isNotEmpty()) {
                        cache.save(query, medicines)
                    }

                    Result.success(
                        MedicineSearchResult(
                            medicines = medicines,
                            fromCache = false
                        )
                    )
                }

                else -> {
                    Result.failure(HttpException(response))
                }
            }
        } catch (exception: Exception) {
            val cached = cache.load(query)

            if (cached != null) {
                Result.success(
                    MedicineSearchResult(
                        medicines = cached,
                        fromCache = true
                    )
                )
            } else {
                Result.failure(exception)
            }
        }
    }
}