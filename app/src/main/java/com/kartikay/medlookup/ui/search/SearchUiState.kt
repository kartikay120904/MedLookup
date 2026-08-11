package com.kartikay.medlookup.ui.search

import com.kartikay.medlookup.domain.model.Medicine

sealed interface SearchUiState {

    data object Initial : SearchUiState

    data object Loading : SearchUiState

    data class Success(
        val medicines: List<Medicine>
    ) : SearchUiState

    data object Empty : SearchUiState

    data class Error(
        val message: String
    ) : SearchUiState
}