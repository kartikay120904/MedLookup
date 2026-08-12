package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.domain.model.Medicine

data class MedicineSearchResult(
    val medicines: List<Medicine>,
    val fromCache: Boolean
)