package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab2.ui.theme.Lab2Theme
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var coalAmount by remember { mutableStateOf("") }
    var fuelOilAmount by remember { mutableStateOf("") }
    var gasAmount by remember { mutableStateOf("") }

    var resultCoal by remember { mutableStateOf(0.0) }
    var resultFuelOil by remember { mutableStateOf(0.0) }
    var resultGas by remember { mutableStateOf(0.0) }

    var coalParticulate = calculateEmissionFactor(20.47, 0.8, 25.20, 1.5, 0.985)
    var fuelOilParticulate = calculateEmissionFactor(39.48, 1.0, 0.15, 0.0, 0.985)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Emission Calculator", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = coalAmount,
            onValueChange = { coalAmount = it },
            label = { Text("Amount of coal (tons)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fuelOilAmount,
            onValueChange = { fuelOilAmount = it },
            label = { Text("Amount of fuel oil (tons)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = gasAmount,
            onValueChange = { gasAmount = it },
            label = { Text("Amount of natural gas (cubic meters)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                resultCoal = calculateCoalEmission(coalAmount.toDoubleOrNull() ?: 0.0, coalParticulate, 20.47)
                resultFuelOil = calculateFuelOilEmission(fuelOilAmount.toDoubleOrNull() ?: 0.0, fuelOilParticulate, 39.48)
                resultGas = calculateGasEmission(gasAmount.toDoubleOrNull() ?: 0.0)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Results
        Text(text = "Coal emission: ${resultCoal} tons")
        Text(text = "Fuel oil emission: ${resultFuelOil} tons")
        Text(text = "Gas emission: ${resultGas} tons")
    }
}

fun calculateCoalEmission(amount: Double, emissionFactor: Double, heatingValue: Double): Double {
    return 1e-6 * amount * heatingValue * emissionFactor
}

fun calculateFuelOilEmission(amount: Double, emissionFactor: Double, heatingValue: Double): Double {
    return 1e-6 * amount * heatingValue * emissionFactor
}

fun calculateGasEmission(amount: Double): Double {
    return 0.0 // No emissions for natural gas
}

fun calculateEmissionFactor(
    Q: Double,
    a: Double,
    A: Double,
    gamma: Double,  // Heat loss due to incomplete combustion of volatile matter
    etaZU: Double,  // Dust collection system efficiency
): Double {
    return (1e+6 / Q) * a * (A / (100 - gamma)) * (1 - etaZU)
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorApp()
}