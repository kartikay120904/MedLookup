package com.kartikay.medlookup.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kartikay.medlookup.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState =
        MutableStateFlow<SearchUiState>(SearchUiState.Initial)

    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var lastQuery = ""

    init {
        observeSearchQuery()
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun retry() {
        if (lastQuery.isNotBlank()) {
            performSearch(lastQuery)
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _query
                .debounce(400L)
                .map { it.trim() }
                .distinctUntilChanged()
                .collectLatest { query ->

                    if (query.isBlank()) {
                        lastQuery = ""
                        _uiState.value = SearchUiState.Initial
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    private fun performSearch(query: String) {
        lastQuery = query

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            repository.searchMedicines(query)
                .onSuccess { medicines ->
                    if (medicines.isEmpty()) {
                        _uiState.value = SearchUiState.Empty
                    } else {
                        _uiState.value = SearchUiState.Success(medicines)
                    }
                }
                .onFailure { error ->
                    _uiState.value = SearchUiState.Error(
                        error.message ?: "Something went wrong"
                    )
                }
        }
    }
}