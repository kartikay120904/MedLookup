package com.kartikay.medlookup.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kartikay.medlookup.domain.model.Medicine
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.medicineDataStore by preferencesDataStore(
    name = "medicine_cache"
)

class MedicineCache(
    private val context: Context
) : MedicineCacheDataSource {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun save(
        query: String,
        medicines: List<Medicine>
    ) {
        val key = stringPreferencesKey(
            "query_${query.lowercase()}"
        )

        val encoded = json.encodeToString(medicines)

        context.medicineDataStore.edit { preferences ->
            preferences[key] = encoded
        }
    }

    override suspend fun load(
        query: String
    ): List<Medicine>? {
        val key = stringPreferencesKey(
            "query_${query.lowercase()}"
        )

        val encoded = context.medicineDataStore.data
            .first()[key]
            ?: return null

        return runCatching {
            json.decodeFromString<List<Medicine>>(encoded)
        }.getOrNull()
    }
}