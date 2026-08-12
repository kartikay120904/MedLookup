package com.kartikay.medlookup.data.repository

import com.kartikay.medlookup.testMedicine
import okhttp3.ResponseBody.Companion.toResponseBody
import com.kartikay.medlookup.data.local.FakeMedicineCache
import com.kartikay.medlookup.data.remote.FakeFdaApi
import com.kartikay.medlookup.data.remote.FdaDrug
import com.kartikay.medlookup.data.remote.FdaMeta
import com.kartikay.medlookup.data.remote.FdaResultMeta
import com.kartikay.medlookup.data.remote.OpenFda
import com.kartikay.medlookup.data.remote.FdaResponse
import com.kartikay.medlookup.testMedicine
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MedicineRepositoryTest {

    private lateinit var api: FakeFdaApi
    private lateinit var cache: FakeMedicineCache
    private lateinit var repository: MedicineRepository

    @Before
    fun setup() {
        api = FakeFdaApi()
        cache = FakeMedicineCache()

        repository = MedicineRepository(
            api = api,
            cache = cache
        )
    }

    @Test
    fun `successful response returns medicines from api`() = runTest {
        api.response = Response.success(
            FdaResponse(
                meta = FdaMeta(
                    results = FdaResultMeta(
                        skip = 0,
                        limit = 20,
                        total = 1
                    )
                ),
                results = listOf(
                    FdaDrug(
                        id = "test-1",
                        openfda = OpenFda(
                            brand_name = listOf("Test Aspirin"),
                            generic_name = listOf("Aspirin"),
                            manufacturer_name = listOf("Test Manufacturer"),
                            route = listOf("ORAL"),
                            product_type = listOf("HUMAN OTC DRUG")
                        ),
                        purpose = listOf("Pain relief"),
                        indications_and_usage = null,
                        dosage_and_administration = null,
                        warnings = null,
                        do_not_use = null,
                        stop_use = null,
                        active_ingredient = null,
                        inactive_ingredient = null,
                        storage_and_handling = null
                    )
                )
            )
        )

        val result = repository.searchMedicines("aspirin")

        assertTrue(result.isSuccess)

        val searchResult = result.getOrThrow()

        assertFalse(searchResult.fromCache)
        assertEquals(1, searchResult.medicines.size)
        assertEquals(
            "Test Aspirin",
            searchResult.medicines.first().brandName
        )
    }

    @Test
    fun `404 response returns empty results`() = runTest {
        api.response = Response.error(
            404,
            "Not found".toResponseBody(null)
        )

        val result = repository.searchMedicines("unknown")

        assertTrue(result.isSuccess)

        val searchResult = result.getOrThrow()

        assertTrue(searchResult.medicines.isEmpty())
        assertFalse(searchResult.fromCache)
    }

    @Test
    fun `network failure returns cached medicines`() = runTest {
        val cachedMedicine = testMedicine()

        cache.save(
            query = "aspirin",
            medicines = listOf(cachedMedicine)
        )

        api.exception = RuntimeException("No internet")

        val result = repository.searchMedicines("aspirin")

        assertTrue(result.isSuccess)

        val searchResult = result.getOrThrow()

        assertTrue(searchResult.fromCache)
        assertEquals(
            listOf(cachedMedicine),
            searchResult.medicines
        )
    }

    @Test
    fun `network failure without cache returns failure`() = runTest {
        api.exception = RuntimeException("No internet")

        val result = repository.searchMedicines("unknown")

        assertTrue(result.isFailure)
    }
}