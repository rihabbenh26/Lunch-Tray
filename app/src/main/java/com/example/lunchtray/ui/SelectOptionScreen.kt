package com.example.lunchtray.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lunchtray.Route
import java.text.NumberFormat

@Composable
fun SelectOptionScreen(
    currentScreen: Route,
    viewModel: OrderViewModel,
    options: Map<String, OrderViewModel.MenuItem>,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onSelectionButtonClicked: (String) -> Unit,
) {
    var selectedOption by rememberSaveable { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
        ) {
            options.forEach { (item, menuItem) ->
                val isSelected = selectedOption == item
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = isSelected,
                            onClick = {
                                selectedOption = item
                                onSelectionButtonClicked(item)
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = null
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Text(text = menuItem.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = menuItem.description, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = NumberFormat.getCurrencyInstance().format(menuItem.price),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Divider()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { onCancelButtonClicked() }) {
                    Text("CANCEL")
                }
                Button(
                    onClick = { onNextButtonClicked() },
                    enabled = selectedOption.isNotEmpty()
                ) {
                    Text("NEXT")
                }
            }
        }
    }
}