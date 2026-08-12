package com.kartikay.medlookup.ui.detail
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kartikay.medlookup.domain.model.Medicine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(
    medicine: Medicine,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = medicine.brandName.ifBlank {
                            "Medicine details"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.semantics {
                            contentDescription = "Back to search results"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                MedicineSummary(medicine)
            }

            medicine.purpose?.takeIf { it.isNotBlank() }?.let { text ->
                item {
                    LabelSection(
                        title = "Purpose",
                        content = text
                    )
                }
            }

            medicine.indicationsAndUsage
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Indications & Usage",
                            content = text
                        )
                    }
                }

            medicine.dosageAndAdministration
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Dosage & Administration",
                            content = text
                        )
                    }
                }

            medicine.warnings
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Warnings",
                            content = text
                        )
                    }
                }

            medicine.doNotUse
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Do Not Use",
                            content = text
                        )
                    }
                }

            medicine.stopUse
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Stop Use",
                            content = text
                        )
                    }
                }

            medicine.activeIngredient
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Active Ingredients",
                            content = text
                        )
                    }
                }

            medicine.inactiveIngredient
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Inactive Ingredients",
                            content = text
                        )
                    }
                }

            medicine.storageAndHandling
                ?.takeIf { it.isNotBlank() }
                ?.let { text ->
                    item {
                        LabelSection(
                            title = "Storage & Handling",
                            content = text
                        )
                    }
                }

            item {
                Disclaimer()
            }
        }
    }
}

@Composable
private fun MedicineSummary(
    medicine: Medicine
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = medicine.brandName.ifBlank { "Not available" },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        InfoRow(
            label = "Generic name",
            value = medicine.genericName
        )

        InfoRow(
            label = "Manufacturer",
            value = medicine.manufacturer
        )

        InfoRow(
            label = "Route",
            value = medicine.route
        )

        InfoRow(
            label = "Product type",
            value = medicine.productType
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = value.ifBlank { "Not available" },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun LabelSection(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider()
    }
}

@Composable
private fun Disclaimer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Medical disclaimer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "This information comes from the FDA drug label database " +
                    "and has not been independently validated by MedLookup. " +
                    "It is provided for informational purposes only and is not " +
                    "medical advice. Consult a qualified healthcare professional " +
                    "for medical guidance."
        )
    }
}