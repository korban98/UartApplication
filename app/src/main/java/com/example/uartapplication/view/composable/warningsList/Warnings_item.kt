package com.example.uartapplication.view.composable.warningsList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Composable che rappresenta un singolo elemento nella lista dei warning.
 *
 * @param stringWarning Descrizione dell'errore da visualizzare.
 */
@Composable
fun WarningsItem(stringWarning: String) {
    // UI del singolo elemento della lista degli errori
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icona di avvertimento
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(width = 12.dp))
            // Testo dell'errore
            Text(text = stringWarning, color = Color.White)
        }
    }
}