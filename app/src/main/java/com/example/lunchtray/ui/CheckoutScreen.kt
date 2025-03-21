package com.example.lunchtray.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lunchtray.Route


import java.text.NumberFormat

@Composable
fun CheckoutScreen(
    currentScreen: Route,
    viewModel: OrderViewModel,
    onCancelButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit
) {
    val selectedEntree by viewModel.selectedEntree.observeAsState("")
    val selectedSide by viewModel.selectedSide.observeAsState("")
    val selectedAccompaniment by viewModel.selectedAccompaniment.observeAsState("")
    val price by viewModel.price.observeAsState(0.0)

    val entreePrice = viewModel.entreeOptions.observeAsState().value?.get(selectedEntree)?.price ?: 0.0
    val sidePrice = viewModel.sideOptions.observeAsState().value?.get(selectedSide)?.price ?: 0.0
    val accompanimentPrice = viewModel.accompanimentOptions.observeAsState().value?.get(selectedAccompaniment)?.price ?: 0.0

    val tax = viewModel.calculateTax()
    val total = viewModel.calculateTotal()

    val currencyFormatter = NumberFormat.getCurrencyInstance()

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                currentScreen = currentScreen,
                canNavigateBack = true,
                navigateUp = onCancelButtonClicked
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Order Summary", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            // Order items
            if (selectedEntree.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedEntree)
                    Text(currencyFormatter.format(entreePrice))
                }
            }

            if (selectedSide.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedSide)
                    Text(currencyFormatter.format(sidePrice))
                }
            }

            if (selectedAccompaniment.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedAccompaniment)
                    Text(currencyFormatter.format(accompanimentPrice))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Total section
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal:")
                Text(currencyFormatter.format(price))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tax:")
                Text(currencyFormatter.format(tax))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.titleLarge)
                Text(currencyFormatter.format(total), style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { onCancelButtonClicked() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("CANCEL")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { onSubmitButtonClicked() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("SUBMIT")
                }
            }
        }
    }
}