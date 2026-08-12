package com.kartikay.medlookup

import com.kartikay.medlookup.domain.model.Medicine

fun testMedicine(
    id: String = "test-1",
    brandName: String = "Test Aspirin"
): Medicine {
    return Medicine(
        id = id,
        brandName = brandName,
        genericName = "Aspirin",
        manufacturer = "Test Manufacturer",
        route = "ORAL",
        productType = "HUMAN OTC DRUG",
        purpose = "Pain relief",
        indicationsAndUsage = "Temporary relief of minor aches",
        dosageAndAdministration = "Take as directed",
        warnings = "Keep out of reach of children",
        doNotUse = null,
        stopUse = null,
        activeIngredient = "Aspirin",
        inactiveIngredient = "Starch",
        storageAndHandling = "Store at room temperature"
    )
}