package com.example.z_u1_moreno_ledesma_ximena.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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