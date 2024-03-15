package com.example.uartapplication.view.composable.batteryCanvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Composable che rappresenta una batteria grafica.
 *
 * @param modifier Modificatore da applicare al composable.
 * @param value Valore percentuale della batteria (da 0 a 100).
 * @param steps Numero di passaggi/divisioni della barra della batteria.
 * @param outerThickness Spessore della cornice esterna della batteria.
 * @param totalBarSpace Spazio totale tra le barre della batteria.
 * @param colore Colore della batteria in base al livello di carica.
 * @param knobLength Lunghezza del terminale superiore della batteria.
 * @param batteryHeightMultiplier Moltiplicatore per regolare l'altezza della batteria.
 * @param batteryWidthMultiplier Moltiplicatore per regolare la larghezza della batteria.
 */
@Composable
fun Battery(
    modifier: Modifier = Modifier,
    value: Int = 0,
    steps: Int = 10,
    outerThickness: Float = 5f,
    totalBarSpace: Float = 20f,
    colore: Color = when {
        value == 100 -> {
            Color.Green
        }

        value in 50..99 -> {
            Color(0xFFCDFF39)
        }

        value in 21..49 -> {
            Color.Yellow
        }

        value <= 20 -> {
            Color.Red
        }

        else -> {
            Color.Gray
        }
    },
    knobLength: Float = 10f,
    batteryHeightMultiplier: Float = 1.5f,
    batteryWidthMultiplier: Float = 0.8f
) {
    // Disegna la batteria grafica
    Canvas(
        modifier = modifier
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Disegna il corpo principale della batteria
        drawRect(
            color = colore,
            size = Size(
                width = canvasWidth * batteryWidthMultiplier,
                height = canvasHeight * batteryHeightMultiplier
            ),
            style = Stroke(
                width = outerThickness,
                pathEffect = PathEffect.cornerPathEffect(5.dp.toPx())
            )
        )

        // Disegna la parte superiore della batteria
        drawRoundRect(
            color = colore,
            topLeft = Offset((canvasWidth/2) * 0.65f, -knobLength * 1f) * batteryWidthMultiplier,
            size = Size(canvasWidth * 0.35f, knobLength) * batteryWidthMultiplier,
            cornerRadius = CornerRadius(10f, 10f)
        )

        val innerBatteryHeight = canvasHeight * batteryHeightMultiplier - outerThickness
        val spaceBetween = totalBarSpace / (steps + 1)
        val loadingBarHeight = (innerBatteryHeight - totalBarSpace) / steps

        // Inizializza gli offset per disegnare le barre di carica
        var currentStartOffset = Offset(
            x = outerThickness,
            y = canvasHeight * (0 + batteryHeightMultiplier) / 1 - (outerThickness / 0.25f) - spaceBetween
        )

        var currentEndOffset = Offset(
            x = canvasWidth * batteryWidthMultiplier - outerThickness,
            y = canvasHeight * (0 + batteryHeightMultiplier) / 1 - (outerThickness / 0.25f) - spaceBetween
        )

        // Disegna le barre di carica in base al valore percentuale
        for (i in 0 until (value / 100f * steps).roundToInt()) {
            drawLine(
                color = colore,
                strokeWidth = loadingBarHeight,
                start = currentStartOffset,
                end = currentEndOffset
            )
            currentStartOffset =
                currentStartOffset.copy(y = currentStartOffset.y - loadingBarHeight - spaceBetween)
            currentEndOffset =
                currentEndOffset.copy(y = currentEndOffset.y - loadingBarHeight - spaceBetween)
        }
    }
}