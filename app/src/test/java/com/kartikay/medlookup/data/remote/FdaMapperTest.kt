package com.kartikay.medlookup.data.remote

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FdaMapperTest {

    @Test
    fun `messy FDA payload maps safely to domain model`() {
        val drug = FdaDrug(
            id = "test-123",
            openfda = OpenFda(
                brand_name = listOf("Aspirin"),
                generic_name = listOf("aspirin"),
                manufacturer_name = emptyList(),
                route = listOf("ORAL"),
                product_type = null
            ),
            purpose = listOf(
                "Pain relief",
                "Fever reduction"
            ),
            indications_and_usage = listOf(
                "Used for temporary relief."
            ),
            dosage_and_administration = null,
            warnings = listOf(
                "Keep out of reach of children.",
                "Read the label carefully."
            ),
            do_not_use = null,
            stop_use = null,
            active_ingredient = listOf("Aspirin 325 mg"),
            inactive_ingredient = emptyList(),
            storage_and_handling = listOf("Store at room temperature.")
        )

        val medicine = drug.toDomainOrNull()

        requireNotNull(medicine)

        assertEquals("test-123", medicine.id)
        assertEquals("Aspirin", medicine.brandName)
        assertEquals("aspirin", medicine.genericName)

        // Empty API array becomes an empty string.
        assertEquals("", medicine.manufacturer)

        assertEquals("ORAL", medicine.route)

        // Missing API field becomes an empty string.
        assertEquals("", medicine.productType)

        // Multiple values are joined into readable text.
        assertEquals(
            "Pain relief\n\nFever reduction",
            medicine.purpose
        )

        assertEquals(
            "Keep out of reach of children.\n\nRead the label carefully.",
            medicine.warnings
        )

        // Missing optional sections remain null.
        assertNull(medicine.dosageAndAdministration)
        assertNull(medicine.doNotUse)
        assertNull(medicine.stopUse)
    }

    @Test
    fun `drug without id is discarded`() {
        val drug = FdaDrug(
            id = null,
            openfda = OpenFda(
                brand_name = listOf("Aspirin"),
                generic_name = listOf("aspirin"),
                manufacturer_name = listOf("Test Manufacturer"),
                route = listOf("ORAL"),
                product_type = listOf("HUMAN OTC DRUG")
            ),
            purpose = null,
            indications_and_usage = null,
            dosage_and_administration = null,
            warnings = null,
            do_not_use = null,
            stop_use = null,
            active_ingredient = null,
            inactive_ingredient = null,
            storage_and_handling = null
        )

        assertNull(drug.toDomainOrNull())
    }
}