package com.kartikay.medlookup.ui.search

import com.kartikay.medlookup.data.repository.FakeMedicineRepository
import com.kartikay.medlookup.data.repository.MedicineSearchResult
import com.kartikay.medlookup.testMedicine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: FakeMedicineRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeMedicineRepository()
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `blank query shows initial state`() = runTest {
        viewModel.onQueryChanged("")

        advanceTimeBy(500)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value is SearchUiState.Initial
        )
    }

    @Test
    fun `single character does not trigger search`() = runTest {
        viewModel.onQueryChanged("a")

        advanceTimeBy(500)
        advanceUntilIdle()

        assertEquals(
            null,
            repository.lastQuery
        )

        assertTrue(
            viewModel.uiState.value is SearchUiState.Initial
        )
    }

    @Test
    fun `two characters trigger search after debounce`() = runTest {
        val medicine = testMedicine()

        repository.result = Result.success(
            MedicineSearchResult(
                medicines = listOf(medicine),
                fromCache = false
            )
        )

        viewModel.onQueryChanged("as")

        advanceTimeBy(399)

        assertEquals(
            null,
            repository.lastQuery
        )

        advanceTimeBy(1)
        advanceUntilIdle()

        assertEquals(
            "as",
            repository.lastQuery
        )

        assertTrue(
            viewModel.uiState.value is SearchUiState.Success
        )
    }

    @Test
    fun `empty repository result produces empty state`() = runTest {
        repository.result = Result.success(
            MedicineSearchResult(
                medicines = emptyList(),
                fromCache = false
            )
        )

        viewModel.onQueryChanged("xyz")

        advanceTimeBy(400)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value is SearchUiState.Empty
        )
    }

    @Test
    fun `repository failure produces error state`() = runTest {
        repository.result = Result.failure(
            RuntimeException("Network unavailable")
        )

        viewModel.onQueryChanged("aspirin")

        advanceTimeBy(400)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value is SearchUiState.Error
        )
    }

    @Test
    fun `cached result produces success state marked from cache`() = runTest {
        val medicine = testMedicine()

        repository.result = Result.success(
            MedicineSearchResult(
                medicines = listOf(medicine),
                fromCache = true
            )
        )

        viewModel.onQueryChanged("aspirin")

        advanceTimeBy(400)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(
            state is SearchUiState.Success
        )

        state as SearchUiState.Success

        assertTrue(state.fromCache)
        assertEquals(
            1,
            state.medicines.size
        )
    }
}