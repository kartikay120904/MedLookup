package com.kartikay.medlookup.ui.search

import com.kartikay.medlookup.data.repository.MedicineRepositoryContract
import com.kartikay.medlookup.data.repository.MedicineSearchResult

class FakeMedicineRepositoryForUi(
    private val state: SearchUiState
) : MedicineRepositoryContract {

    override suspend fun searchMedicines(
        query: String
    ): Result<MedicineSearchResult> {
        return when (state) {
            is SearchUiState.Success -> {
                Result.success(
                    MedicineSearchResult(
                        medicines = state.medicines,
                        fromCache = state.fromCache
                    )
                )
            }

            is SearchUiState.Empty -> {
                Result.success(
                    MedicineSearchResult(
                        medicines = emptyList(),
                        fromCache = false
                    )
                )
            }

            is SearchUiState.Error -> {
                Result.failure(
                    RuntimeException(state.message)
                )
            }

            else -> {
                Result.success(
                    MedicineSearchResult(
                        medicines = emptyList(),
                        fromCache = false
                    )
                )
            }
        }
    }
}