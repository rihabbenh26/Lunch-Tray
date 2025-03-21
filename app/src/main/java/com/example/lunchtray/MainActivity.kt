package com.example.lunchtray

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunchTrayApp()
        }
    }
}

@Composable
fun LunchTrayApp() {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Route.START.name
    ) {
        composable(Route.START.name) {
            StartOrderScreen(
                currentScreen = Route.START,
                onStartOrderClicked = { navController.navigate(Route.ENTREE.name) }
            )
        }

        composable(Route.ENTREE.name) {
            val entreeOptions by viewModel.entreeOptions.observeAsState(mapOf())

            SelectOptionScreen(
                currentScreen = Route.ENTREE,
                viewModel = viewModel,
                options = entreeOptions,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.navigateUp()
                },
                onNextButtonClicked = { navController.navigate(Route.SIDE.name) },
                onSelectionButtonClicked = { viewModel.setEntree(it) }
            )
        }

        composable(Route.SIDE.name) {
            val sideOptions by viewModel.sideOptions.observeAsState(mapOf())

            SelectOptionScreen(
                currentScreen = Route.SIDE,
                viewModel = viewModel,
                options = sideOptions,
                onCancelButtonClicked = { navController.navigateUp() },
                onNextButtonClicked = { navController.navigate(Route.ACCOMPANIMENT.name) },
                onSelectionButtonClicked = { viewModel.setSide(it) }
            )
        }

        composable(Route.ACCOMPANIMENT.name) {
            val accompanimentOptions by viewModel.accompanimentOptions.observeAsState(mapOf())

            SelectOptionScreen(
                currentScreen = Route.ACCOMPANIMENT,
                viewModel = viewModel,
                options = accompanimentOptions,
                onCancelButtonClicked = { navController.navigateUp() },
                onNextButtonClicked = { navController.navigate(Route.CHECKOUT.name) },
                onSelectionButtonClicked = { viewModel.setAccompaniment(it) }
            )
        }

        composable(Route.CHECKOUT.name) {
            CheckoutScreen(
                currentScreen = Route.CHECKOUT,
                viewModel = viewModel,
                onCancelButtonClicked = { navController.navigateUp() },
                onSubmitButtonClicked = {
                    // Process order
                    viewModel.resetOrder()
                    navController.navigate(Route.START.name) {
                        popUpTo(Route.START.name) { inclusive = true }
                    }
                }
            )
        }
    }
}