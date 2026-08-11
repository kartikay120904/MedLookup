package com.kartikay.medlookup.domain.model

data class Medicine(
    val id: String,
    val brandName: String,
    val genericName: String,
    val manufacturer: String,
    val route: String,
    val productType: String,

    val purpose: String?,
    val indicationsAndUsage: String?,
    val dosageAndAdministration: String?,
    val warnings: String?,
    val doNotUse: String?,
    val stopUse: String?,
    val activeIngredient: String?,
    val inactiveIngredient: String?,
    val storageAndHandling: String?
)