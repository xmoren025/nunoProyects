package com.example.z_u1_moreno_ledesma_ximena.calculators

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.z_u1_moreno_ledesma_ximena.components.ResultCard
import com.example.z_u1_moreno_ledesma_ximena.components.ResultsCompare
import java.text.DecimalFormat

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