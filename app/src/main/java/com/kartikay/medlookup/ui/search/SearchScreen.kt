package com.kartikay.medlookup.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kartikay.medlookup.domain.model.Medicine

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onMedicineClick: (Medicine) -> Unit
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MedLookup",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics {
                    contentDescription = "Search medicines"
                },
            label = {
                Text("Search medicines")
            },
            singleLine = true
        )

        SearchContent(
            state = uiState,
            onRetry = viewModel::retry,
            onMedicineClick = onMedicineClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun SearchContent(
    state: SearchUiState,
    onRetry: () -> Unit,
    onMedicineClick: (Medicine) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        SearchUiState.Initial -> {
            InitialContent(modifier)
        }

        SearchUiState.Loading -> {
            LoadingContent(modifier)
        }

        is SearchUiState.Success -> {
            MedicineList(
                medicines = state.medicines,
                onMedicineClick = onMedicineClick,
                modifier = modifier
            )
        }

        SearchUiState.Empty -> {
            EmptyContent(modifier)
        }

        is SearchUiState.Error -> {
            ErrorContent(
                message = state.message,
                onRetry = onRetry,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun InitialContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Search for a medicine",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Enter a brand name to view official FDA label information.",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                contentDescription = "Loading medicines"
            }
        )

        Text(
            text = "Searching...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No medicines found",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Try a different brand name.",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Search failed",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = message,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onRetry,
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics {
                    contentDescription = "Retry medicine search"
                }
        ) {
            Text("Try again")
        }
    }
}

@Composable
private fun MedicineList(
    medicines: List<Medicine>,
    onMedicineClick: (Medicine) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = medicines,
            key = { it.id }
        ) { medicine ->
            MedicineRow(
                medicine = medicine,
                onClick = {
                    onMedicineClick(medicine)
                }
            )
        }
    }
}

@Composable
private fun MedicineRow(
    medicine: Medicine,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = medicine.brandName.ifBlank { "Not available" },
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Generic: ${
                    medicine.genericName.ifBlank { "Not available" }
                }"
            )

            Text(
                text = "Manufacturer: ${
                    medicine.manufacturer.ifBlank { "Not available" }
                }"
            )
        }
    }
}