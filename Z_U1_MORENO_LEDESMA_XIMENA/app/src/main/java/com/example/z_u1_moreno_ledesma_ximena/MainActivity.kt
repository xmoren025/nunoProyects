package com.example.z_u1_moreno_ledesma_ximena

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.z_u1_moreno_ledesma_ximena.calculators.ItemsCalculator
import com.example.z_u1_moreno_ledesma_ximena.calculators.ItemsWithVolumeCalculator
import com.example.z_u1_moreno_ledesma_ximena.calculators.ItemsWithWeightCalculator
import com.example.z_u1_moreno_ledesma_ximena.calculators.VolumeCalculator
import com.example.z_u1_moreno_ledesma_ximena.calculators.WeightCalculator
import com.example.z_u1_moreno_ledesma_ximena.components.TypeSpinner
import com.example.z_u1_moreno_ledesma_ximena.ui.theme.Z_U1_MORENO_LEDESMA_XIMENATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Z_U1_MORENO_LEDESMA_XIMENATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitPriceCalculator()
                }
            }
        }
    }
}

@Composable
fun UnitPriceCalculator() {
    val types = listOf(
        "Weight", "Volume", "Items", "Items with weight", "Items with volume"
    )

    val units = listOf(
        "micrograms (μg)", "milligrams (mg)", "grams (g)", "kilograms (kg)",
        "metric tons (t)", "grains (gr)", "drachms (dr)", "ounces (oz)",
        "pounds (lb)", "stones (st)", "US short tons (US ton)",
        "imperial tons (long ton)", "troy ounces (oz t)"
    )

    val volumes = listOf(
        "cubic meters (m³)", "cubic feet (ft)", "cubic yards (cu yd)",
        "milliliters (ml)", "liters (l)", "gallons (US) (US gal)", "gallons (UK) (UK gal)",
        "fluid ounces (US) (US fl oz)", "fluid ounces (UK) (UK fl oz)", "US customary cups (cups)",
        "quarts (US) (US qt)", "quarts (UK) (UK qt)", "pints (US) (US pt)", "pints (UK) (UK pt)"
    )

    var selectedType by remember { mutableStateOf("Weight") }


    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        Text(
            text = "Calculadora de Precio Unitario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
        )

        Text(
            text = "Type",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 1.dp)
        )

        TypeSpinner(
            types = types,
            selectedType = selectedType,
            onTypeSelected = { selectedType = it }
        )

        when (selectedType) {
            "Weight" -> WeightCalculator(units)
            "Volume" -> VolumeCalculator(volumes)
            "Items" -> ItemsCalculator()
            "Items with weight" -> ItemsWithWeightCalculator(units)
            "Items with volume" -> ItemsWithVolumeCalculator(volumes)
        }

    }
}















