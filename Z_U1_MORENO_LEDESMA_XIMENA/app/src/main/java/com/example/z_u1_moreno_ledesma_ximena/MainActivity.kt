package com.example.z_u1_moreno_ledesma_ximena

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import java.text.DecimalFormat
import com.example.z_u1_moreno_ledesma_ximena.ui.theme.Z_U1_MORENO_LEDESMA_XIMENATheme
import kotlin.math.abs

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

@Composable
fun WeightCalculator(
    units: List<String>
) {
    val context = LocalContext.current
    val showToast = remember { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } }

    var priceA by remember { mutableStateOf("") }
    var weightA by remember { mutableStateOf("") }
    var selectedUnitA by remember { mutableStateOf("kilograms (kg)") }
    var resultA by remember { mutableStateOf("") }

    var priceB by remember { mutableStateOf("") }
    var weightB by remember { mutableStateOf("") }
    var selectedUnitB by remember { mutableStateOf("kilograms (kg)") }
    var resultB by remember { mutableStateOf("") }

    Column {
        // Option A
        Text(
            text = "Option A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 3.dp, top = 6.dp)
        )

        UnitSpinner(
            units = units,
            selectedUnit = selectedUnitA,
            onUnitSelected = { selectedUnitA = it }
        )

        OutlinedTextField(
            value = weightA,
            onValueChange = { weightA = it },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = priceA,
            onValueChange = { priceA = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceA.isBlank() || weightA.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultA = ""
                    return@Button
                }
                try {
                    val priceValue = priceA.toDouble()
                    val weightValue = weightA.toDouble()

                    if (weightValue <= 0.0) {
                        showToast("El peso debe ser mayor que cero")
                        resultA = ""
                        return@Button
                    }

                    val unitPrice = priceValue / weightValue
                    val df = DecimalFormat("#.##")
                    resultA = "Precio unitario: $${df.format(unitPrice)} por $selectedUnitA"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultA = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option A")
        }

        if (resultA.isNotEmpty()) {
            ResultCard(resultA)
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )

        // Option B
        Text(
            text = "Option B",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        UnitSpinner(
            units = units,
            selectedUnit = selectedUnitB,
            onUnitSelected = { selectedUnitB = it }
        )

        OutlinedTextField(
            value = weightB,
            onValueChange = { weightB = it },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = priceB,
            onValueChange = { priceB = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceB.isBlank() || weightB.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultB = ""
                    return@Button
                }
                try {
                    val priceValue = priceB.toDouble()
                    val weightValue = weightB.toDouble()

                    if (weightValue <= 0.0) {
                        showToast("El peso debe ser mayor que cero")
                        resultB = ""
                        return@Button
                    }

                    val unitPrice = priceValue / weightValue
                    val df = DecimalFormat("#.##")
                    resultB = "Precio unitario: $${df.format(unitPrice)} por $selectedUnitB"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultB = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option B")
        }

        if (resultB.isNotEmpty()) {
            ResultCard(resultB)
        }

        if (resultA.isNotEmpty() && resultB.isNotEmpty()) {
            val unitPriceA = resultA.substringAfter("$").substringBefore(" ").toDouble()
            val unitPriceB = resultB.substringAfter("$").substringBefore(" ").toDouble()
            ResultsCompare(unitPriceA, unitPriceB)}


    }
}

@Composable
fun VolumeCalculator(
    volumes: List<String>
) {
    val context = LocalContext.current
    val showToast = remember { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } }

    var priceA by remember { mutableStateOf("") }
    var volumeA by remember { mutableStateOf("") }
    var selectedVolumeA by remember { mutableStateOf("liters (l)") }
    var resultA by remember { mutableStateOf("") }

    var priceB by remember { mutableStateOf("") }
    var volumeB by remember { mutableStateOf("") }
    var selectedVolumeB by remember { mutableStateOf("liters (l)") }
    var resultB by remember { mutableStateOf("") }

    Column {
        // Option A
        Text(
            text = "Option A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
        )

        VolumeSpinner(
            volumes = volumes,
            selectedVolume = selectedVolumeA,
            onVolumeSelected = { selectedVolumeA = it }
        )

        OutlinedTextField(
            value = volumeA,
            onValueChange = { volumeA = it },
            label = { Text("Volume") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = priceA,
            onValueChange = { priceA = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceA.isBlank() || volumeA.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultA = ""
                    return@Button
                }
                try {
                    val priceValue = priceA.toDouble()
                    val volumeValue = volumeA.toDouble()

                    if (volumeValue <= 0.0) {
                        showToast("El volumen debe ser mayor que cero")
                        resultA = ""
                        return@Button
                    }

                    val unitPrice = priceValue / volumeValue
                    val df = DecimalFormat("#.##")
                    resultA = "Precio unitario: $${df.format(unitPrice)} por $selectedVolumeA"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultA = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option A")
        }

        if (resultA.isNotEmpty()) {
            ResultCard(resultA)
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )

        // Option B
        Text(
            text = "Option B",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        VolumeSpinner(
            volumes = volumes,
            selectedVolume = selectedVolumeB,
            onVolumeSelected = { selectedVolumeB = it }
        )

        OutlinedTextField(
            value = volumeB,
            onValueChange = { volumeB = it },
            label = { Text("Volume") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = priceB,
            onValueChange = { priceB = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceB.isBlank() || volumeB.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultB = ""
                    return@Button
                }
                try {
                    val priceValue = priceB.toDouble()
                    val volumeValue = volumeB.toDouble()

                    if (volumeValue <= 0.0) {
                        showToast("El volumen debe ser mayor que cero")
                        resultB = ""
                        return@Button
                    }

                    val unitPrice = priceValue / volumeValue
                    val df = DecimalFormat("#.##")
                    resultB = "Precio unitario: $${df.format(unitPrice)} por $selectedVolumeB"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultB = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option B")
        }

        if (resultB.isNotEmpty()) {
            ResultCard(resultB)
        }

        if (resultA.isNotEmpty() && resultB.isNotEmpty()) {
            val unitPriceA = resultA.substringAfter("$").substringBefore(" ").toDouble()
            val unitPriceB = resultB.substringAfter("$").substringBefore(" ").toDouble()
            ResultsCompare(unitPriceA, unitPriceB)}
    }
}

@Composable
fun ItemsCalculator() {
    val context = LocalContext.current
    val showToast = remember { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } }

    var priceA by remember { mutableStateOf("") }
    var quantityA by remember { mutableStateOf("") }
    var resultA by remember { mutableStateOf("") }

    var priceB by remember { mutableStateOf("") }
    var quantityB by remember { mutableStateOf("") }
    var resultB by remember { mutableStateOf("") }

    Column {
        // Option A
        Text(
            text = "Option A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
        )

        OutlinedTextField(
            value = quantityA,
            onValueChange = { quantityA = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceA,
            onValueChange = { priceA = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceA.isBlank() || quantityA.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultA = ""
                    return@Button
                }
                try {
                    val priceValue = priceA.toDouble()
                    val quantityValue = quantityA.toDouble()

                    if (quantityValue <= 0.0) {
                        showToast("La cantidad debe ser mayor que cero")
                        resultA = ""
                        return@Button
                    }

                    val unitPrice = priceValue / quantityValue
                    val df = DecimalFormat("#.##")
                    resultA = "Precio por item: $${df.format(unitPrice)}"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultA = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option A")
        }

        if (resultA.isNotEmpty()) {
            ResultCard(resultA)
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )

        // Option B
        Text(
            text = "Option B",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        OutlinedTextField(
            value = quantityB,
            onValueChange = { quantityB = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceB,
            onValueChange = { priceB = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceB.isBlank() || quantityB.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultB = ""
                    return@Button
                }
                try {
                    val priceValue = priceB.toDouble()
                    val quantityValue = quantityB.toDouble()

                    if (quantityValue <= 0.0) {
                        showToast("La cantidad debe ser mayor que cero")
                        resultB = ""
                        return@Button
                    }

                    val unitPrice = priceValue / quantityValue
                    val df = DecimalFormat("#.##")
                    resultB = "Precio por item: $${df.format(unitPrice)}"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultB = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option B")
        }

        if (resultB.isNotEmpty()) {
            ResultCard(resultB)
        }

        if (resultA.isNotEmpty() && resultB.isNotEmpty()) {
            val unitPriceA = resultA.substringAfter("$").substringBefore(" ").toDouble()
            val unitPriceB = resultB.substringAfter("$").substringBefore(" ").toDouble()
            ResultsCompare(unitPriceA, unitPriceB)}
    }
}

@Composable
fun ItemsWithWeightCalculator(
    units: List<String>
) {
    val context = LocalContext.current
    val showToast = remember { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } }

    var priceA by remember { mutableStateOf("") }
    var weightA by remember { mutableStateOf("") }
    var itemsA by remember { mutableStateOf("") }
    var selectedUnitA by remember { mutableStateOf("kilograms (kg)") }
    var resultA by remember { mutableStateOf("") }

    var priceB by remember { mutableStateOf("") }
    var weightB by remember { mutableStateOf("") }
    var itemsB by remember { mutableStateOf("") }
    var selectedUnitB by remember { mutableStateOf("kilograms (kg)") }
    var resultB by remember { mutableStateOf("") }

    Column {
        // Option A
        Text(
            text = "Option A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
        )

        UnitSpinner(
            units = units,
            selectedUnit = selectedUnitA,
            onUnitSelected = { selectedUnitA = it }
        )

        OutlinedTextField(
            value = weightA,
            onValueChange = { weightA = it },
            label = { Text("Weight per item") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = itemsA,
            onValueChange = { itemsA = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceA,
            onValueChange = { priceA = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceA.isBlank() || weightA.isBlank() || itemsA.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultA = ""
                    return@Button
                }
                try {
                    val priceValue = priceA.toDouble()
                    val weightValue = weightA.toDouble()
                    val itemsValue = itemsA.toDouble()

                    if (weightValue <= 0.0 || itemsValue <= 0.0) {
                        showToast("Los valores deben ser mayores que cero")
                        resultA = ""
                        return@Button
                    }

                    val totalWeight = weightValue * itemsValue
                    val unitPrice = priceValue / totalWeight
                    val df = DecimalFormat("#.##")
                    resultA = "Precio unitario: $${df.format(unitPrice)} por $selectedUnitA"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultA = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option A")
        }

        if (resultA.isNotEmpty()) {
            ResultCard(resultA)
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )

        // Option B
        Text(
            text = "Option B",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        UnitSpinner(
            units = units,
            selectedUnit = selectedUnitB,
            onUnitSelected = { selectedUnitB = it }
        )

        OutlinedTextField(
            value = weightB,
            onValueChange = { weightB = it },
            label = { Text("Weight per item") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = itemsB,
            onValueChange = { itemsB = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceB,
            onValueChange = { priceB = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceB.isBlank() || weightB.isBlank() || itemsB.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultB = ""
                    return@Button
                }
                try {
                    val priceValue = priceB.toDouble()
                    val weightValue = weightB.toDouble()
                    val itemsValue = itemsB.toDouble()

                    if (weightValue <= 0.0 || itemsValue <= 0.0) {
                        showToast("Los valores deben ser mayores que cero")
                        resultB = ""
                        return@Button
                    }

                    val totalWeight = weightValue * itemsValue
                    val unitPrice = priceValue / totalWeight
                    val df = DecimalFormat("#.##")
                    resultB = "Precio unitario: $${df.format(unitPrice)} por $selectedUnitB"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultB = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option B")
        }

        if (resultB.isNotEmpty()) {
            ResultCard(resultB)
        }

        if (resultA.isNotEmpty() && resultB.isNotEmpty()) {
            val unitPriceA = resultA.substringAfter("$").substringBefore(" ").toDouble()
            val unitPriceB = resultB.substringAfter("$").substringBefore(" ").toDouble()
            ResultsCompare(unitPriceA, unitPriceB)}
    }
}


@Composable
fun ItemsWithVolumeCalculator(
    volumes: List<String>
) {
    val context = LocalContext.current
    val showToast = remember { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } }

    var priceA by remember { mutableStateOf("") }
    var volumeA by remember { mutableStateOf("") }
    var itemsA by remember { mutableStateOf("") }
    var selectedVolumeA by remember { mutableStateOf("liters (l)") }
    var resultA by remember { mutableStateOf("") }

    var priceB by remember { mutableStateOf("") }
    var volumeB by remember { mutableStateOf("") }
    var itemsB by remember { mutableStateOf("") }
    var selectedVolumeB by remember { mutableStateOf("liters (l)") }
    var resultB by remember { mutableStateOf("") }

    Column {
        // Option A
        Text(
            text = "Option A",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 3.dp, top = 8.dp)
        )

        VolumeSpinner(
            volumes = volumes,
            selectedVolume = selectedVolumeA,
            onVolumeSelected = { selectedVolumeA = it }
        )

        OutlinedTextField(
            value = volumeA,
            onValueChange = { volumeA = it },
            label = { Text("Volume per item") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = itemsA,
            onValueChange = { itemsA = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceA,
            onValueChange = { priceA = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceA.isBlank() || volumeA.isBlank() || itemsA.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultA = ""
                    return@Button
                }
                try {
                    val priceValue = priceA.toDouble()
                    val volumeValue = volumeA.toDouble()
                    val itemsValue = itemsA.toDouble()

                    if (volumeValue <= 0.0 || itemsValue <= 0.0) {
                        showToast("Los valores deben ser mayores que cero")
                        resultA = ""
                        return@Button
                    }

                    val totalVolume = volumeValue * itemsValue
                    val unitPrice = priceValue / totalVolume
                    val df = DecimalFormat("#.##")
                    resultA = "Precio unitario: $${df.format(unitPrice)} por $selectedVolumeA"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultA = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option A")
        }

        if (resultA.isNotEmpty()) {
            ResultCard(resultA)
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )

        // Option B
        Text(
            text = "Option B",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        VolumeSpinner(
            volumes = volumes,
            selectedVolume = selectedVolumeB,
            onVolumeSelected = { selectedVolumeB = it }
        )

        OutlinedTextField(
            value = volumeB,
            onValueChange = { volumeB = it },
            label = { Text("Volume per item") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = itemsB,
            onValueChange = { itemsB = it },
            label = { Text("Number of items") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("1") }
        )

        OutlinedTextField(
            value = priceB,
            onValueChange = { priceB = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("MXN") }
        )

        Button(
            onClick = {
                if (priceB.isBlank() || volumeB.isBlank() || itemsB.isBlank()) {
                    showToast("Por favor completa todos los campos")
                    resultB = ""
                    return@Button
                }
                try {
                    val priceValue = priceB.toDouble()
                    val volumeValue = volumeB.toDouble()
                    val itemsValue = itemsB.toDouble()

                    if (volumeValue <= 0.0 || itemsValue <= 0.0) {
                        showToast("Los valores deben ser mayores que cero")
                        resultB = ""
                        return@Button
                    }

                    val totalVolume = volumeValue * itemsValue
                    val unitPrice = priceValue / totalVolume
                    val df = DecimalFormat("#.##")
                    resultB = "Precio unitario: $${df.format(unitPrice)} por $selectedVolumeB"
                } catch (e: Exception) {
                    showToast("Por favor ingresa valores numéricos válidos")
                    resultB = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Precio Unitario Option B")
        }

        if (resultB.isNotEmpty()) {
            ResultCard(resultB)
        }

        if (resultA.isNotEmpty() && resultB.isNotEmpty()) {
            val unitPriceA = resultA.substringAfter("$").substringBefore(" ").toDouble()
            val unitPriceB = resultB.substringAfter("$").substringBefore(" ").toDouble()
            ResultsCompare(unitPriceA, unitPriceB)}
    }
}

@Composable
fun ResultCard(result: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = result,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TypeSpinner(
    types: List<String>,
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedType,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun UnitSpinner(
    units: List<String>,
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedUnit,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun VolumeSpinner(
    volumes: List<String>,
    selectedVolume: String,
    onVolumeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedVolume,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            volumes.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onVolumeSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }

}

@Composable
fun ResultsCompare(
    resultA: Double,
    resultB: Double
) {
    val comparisonText = remember(resultA, resultB) {
        when {
            resultA < resultB -> "Option A is cheaper. You save $${"%.2f".format(resultB - resultA)}"
            resultB < resultA -> "Option B is cheaper. You save $${"%.2f".format(resultA - resultB)}"
            else -> "Both options have the same unit price"
        }
    }

    if (resultA > 0 && resultB > 0) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = comparisonText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

