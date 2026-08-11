package com.kartikay.medlookup.data.remote

import com.kartikay.medlookup.domain.model.Medicine

fun FdaDrug.toDomainOrNull(): Medicine? {
    val medicineId = id ?: return null

    return Medicine(
        id = medicineId,

        brandName = openfda?.brand_name?.firstOrNull().orEmpty(),
        genericName = openfda?.generic_name?.firstOrNull().orEmpty(),
        manufacturer = openfda?.manufacturer_name?.firstOrNull().orEmpty(),
        route = openfda?.route?.firstOrNull().orEmpty(),
        productType = openfda?.product_type?.firstOrNull().orEmpty(),

        purpose = purpose?.joinToString("\n\n"),
        indicationsAndUsage = indications_and_usage?.joinToString("\n\n"),
        dosageAndAdministration = dosage_and_administration?.joinToString("\n\n"),
        warnings = warnings?.joinToString("\n\n"),
        doNotUse = do_not_use?.joinToString("\n\n"),
        stopUse = stop_use?.joinToString("\n\n"),
        activeIngredient = active_ingredient?.joinToString("\n\n"),
        inactiveIngredient = inactive_ingredient?.joinToString("\n\n"),
        storageAndHandling = storage_and_handling?.joinToString("\n\n")
    )
}