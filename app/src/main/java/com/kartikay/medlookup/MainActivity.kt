package com.kartikay.medlookup
import com.kartikay.medlookup.data.local.MedicineCache
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kartikay.medlookup.data.remote.NetworkModule
import com.kartikay.medlookup.data.repository.MedicineRepository
import com.kartikay.medlookup.domain.model.Medicine
import com.kartikay.medlookup.ui.detail.MedicineDetailScreen
import com.kartikay.medlookup.ui.search.SearchScreen
import com.kartikay.medlookup.ui.search.SearchViewModel
import com.kartikay.medlookup.ui.search.SearchViewModelFactory

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        MedicineRepository(
            api = NetworkModule.api,
            cache = MedicineCache(applicationContext)
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModelFactory(repository)
        )[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            var selectedMedicine by mutableStateOf<Medicine?>(null)

            NavHost(
                navController = navController,
                startDestination = "search"
            ) {
                composable("search") {
                    SearchScreen(
                        viewModel = viewModel,
                        onMedicineClick = { medicine ->
                            selectedMedicine = medicine
                            navController.navigate("detail")
                        }
                    )
                }

                composable("detail") {
                    selectedMedicine?.let { medicine ->
                        MedicineDetailScreen(
                            medicine = medicine,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}