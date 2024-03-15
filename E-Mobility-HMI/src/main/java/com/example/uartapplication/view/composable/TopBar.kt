package com.example.uartapplication.view.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uartapplication.R
import com.example.uartapplication.model.entities.ChargingStates

/**
 * Id dei bottoni di errore.
 */
const val ERROR = 1
const val WARNING = 2
const val FAULT = 3

/**
 * Composable che rappresenta la barra superiore del cruscotto contenente i bottoni
 * per poter visualizzare le liste degli errori.
 *
 * @param col Colore del pulsante degli errori.
 * @param onButtonClicked Callback chiamato quando il pulsante degli errori viene cliccato.
 */
@Composable
fun TopBar(col: Color, chargingState: ChargingStates, onButtonClicked: (Int) -> Unit) {
    // Colore del pulsante degli errori
    var color by remember { mutableStateOf(Color.Red) }
    var currentChargingState by remember { mutableStateOf<ChargingStates?>(null) }

    // Effetto di lancio basato sul colore fornito
    LaunchedEffect(col) {
        color = col
    }

    LaunchedEffect(chargingState) {
        currentChargingState = chargingState
    }

    // UI della barra superiore
    Row(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 10.dp)
    ) {
        // Pulsante degli errori
        SmallFloatingActionButton(
            onClick = {
                if (color != Color.LightGray) {
                    onButtonClicked(ERROR)
                }
            },
            containerColor = if (color == Color.LightGray) color else Color.Red
        ) {
            // Icona del pulsante degli errori
            val errorPainter = painterResource(id = R.drawable.error)
            Icon(errorPainter, "Floating action Errors button.", tint = Color.Black)
        }
        // Pulsante dei warnings
        SmallFloatingActionButton(
            onClick = {
                if (color != Color.LightGray) {
                    onButtonClicked(WARNING)
                }
            },
            containerColor = if (color == Color.LightGray) color else Color.Yellow
        ) {
            // Icona del pulsante dei warnings
            val warningsPainter = painterResource(id = R.drawable.warning)
            Icon(warningsPainter, "Floating action Errors button.", tint = Color.Black)
        }
        // Pulsante dei faults
        SmallFloatingActionButton(
            onClick = {
                if (color != Color.LightGray) {
                    onButtonClicked(FAULT)
                }
            },
            containerColor = if (color == Color.LightGray) color else Color.Yellow
        ) {
            // Icona del pulsante dei faults
            val faultsPainter = painterResource(id = R.drawable.fault)
            Icon(faultsPainter, "Floating action Errors button.", tint = Color.Black)
        }
        Spacer(modifier = Modifier.weight(1f))
        // Indicatore di ricarica
        LargeFloatingActionButton(
            modifier = Modifier
                .size(50.dp),
            onClick = { },
            shape = CircleShape,
            containerColor = Color.Gray
        ) {
            val chargingStationPainter: Painter =
                painterResource(id = R.drawable.charging)

            val flashingTint by animateColorAsState(
                when (currentChargingState) {
                    ChargingStates.NOT_SIGNIFICANT -> Color.White
                    ChargingStates.CHARGING -> Color.Green
                    ChargingStates.BALANCING -> Color.Yellow
                    ChargingStates.END_OF_CHARGE -> Color.Blue
                    else -> { Color.Black }
                }
            )

            Icon(
                painter = chargingStationPainter,
                contentDescription = "Large floating action button",
                tint = flashingTint,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}