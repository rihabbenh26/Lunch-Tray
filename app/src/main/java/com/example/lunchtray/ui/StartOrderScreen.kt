package com.example.lunchtray.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lunchtray.Route

@Composable
fun StartOrderScreen (
    currentScreen: Route,
    onStartOrderClicked: () -> Unit
){
    Scaffold (
        topBar = {
            LunchTrayAppBar(
                currentScreen = currentScreen,
                canNavigateBack = false,
                navigateUp = {}
            )
        }
    ){
        paddingValues -> (
                Column (
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Button(onClick = onStartOrderClicked) {
                        Text("Start Order")
                    }
                }
                )
    }
}