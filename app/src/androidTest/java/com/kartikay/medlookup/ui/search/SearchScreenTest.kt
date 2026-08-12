package com.kartikay.medlookup.ui.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.kartikay.medlookup.domain.model.Medicine
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchScreen_displaysMedicineResults() {
        val medicine = Medicine(
            id = "test-1",
            brandName = "Aspirin",
            genericName = "Aspirin",
            manufacturer = "Test Manufacturer",
            route = "ORAL",
            productType = "HUMAN OTC DRUG",
            purpose = "Pain relief",
            indicationsAndUsage = "Temporary relief",
            dosageAndAdministration = null,
            warnings = null,
            doNotUse = null,
            stopUse = null,
            activeIngredient = "Aspirin",
            inactiveIngredient = null,
            storageAndHandling = null
        )

        composeTestRule.setContent {
            SearchScreenContent(
                state = SearchUiState.Success(
                    medicines = listOf(medicine),
                    fromCache = false
                )
            )
        }

        composeTestRule
            .onNodeWithText("Aspirin")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Generic: Aspirin")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Manufacturer: Test Manufacturer")
            .assertIsDisplayed()
    }

    @Test
    fun searchScreen_displaysErrorAndRetry() {
        var retryClicked = false

        composeTestRule.setContent {
            SearchScreenContent(
                state = SearchUiState.Error(
                    message = "Network unavailable"
                ),
                onRetry = {
                    retryClicked = true
                }
            )
        }

        composeTestRule
            .onNodeWithText("Search failed")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Network unavailable")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Retry medicine search")
            .assertIsDisplayed()
            .performClick()

        assertTrue(retryClicked)
    }
}