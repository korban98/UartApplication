package com.example.uartapplication.view.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.uartapplication.R
import com.example.uartapplication.model.entities.TelemetriesData
import com.example.uartapplication.view.composable.batteryCanvas.Battery

/**
 * Composable che rappresenta il cruscotto effettivo,
 * in cui è possibile visualizzare le informazioni sullo stato del veicolo.
 *
 * @param telemetries Oggetto [TelemetriesData] contenente i dati da visualizzare sul cruscotto.
 */
@Composable
fun Dashboard(telemetries: TelemetriesData?) {
    // Dati delle telemetrie correnti
    var currentTelemetriesData by remember { mutableStateOf<TelemetriesData?>(null) }

    // Flag che indica se è stato premuto il pulsante delle informazioni sulla batteria
    var batteryInfoClicked by remember { mutableStateOf(false) }

    // Flag che indica se è stato premuto il pulsante delle informazioni sul motore
    var motorInfoClicked by remember { mutableStateOf(false) }

    // Effetto di lancio basato sulle telemetrie correnti
    LaunchedEffect(telemetries) {
        currentTelemetriesData = telemetries
    }

    // UI principale del cruscotto
    Row {
        // Parte sinistra del cruscotto con informazioni sul motore
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, top = 11.dp, end = 10.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(25.dp))
                .weight(1f),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Black,
            )
        ) {
            // Se è stato premuto il pulsante delle informazioni sul motore, mostra un popup con dettagli aggiuntivi
            if (motorInfoClicked) {
                Popup(
                    alignment = Alignment.TopCenter,
                    onDismissRequest = { motorInfoClicked = false }
                ) {
                    // Popup con informazioni sul motore
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .wrapContentHeight()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(25.dp)),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Key status: ${currentTelemetriesData?.keyStatus}")
                            Text("Left temperature: ${currentTelemetriesData?.motorTemperatureL ?: 0}°C")
                            Text("Right temperature: ${currentTelemetriesData?.motorTemperatureR ?: 0}°C")
                        }
                    }
                }
            }
            // Icona per attivare il popup delle informazioni sul motore
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Motor Info",
                tint = Color.White,
                modifier = Modifier
                    .size(80.dp)
                    .padding(20.dp)
                    .align(Alignment.End)
                    .clickable { motorInfoClicked = true }
            )
            // Contenuto principale con l'immagine del veicolo e informazioni sulla velocità e marcia attuale
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                // Icona del veicolo al centro
                val transparentCarPainter: Painter =
                    painterResource(id = R.drawable.car_transparent)
                Image(
                    painter = transparentCarPainter,
                    contentDescription = "Car Image",
                    modifier = Modifier
                        .height(100.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
                // Riga inferiore con l'indicatore di ricarica e la velocità
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 60.sp
                            ).toSpanStyle()
                        ) {
                            append("${currentTelemetriesData?.velocityValue ?: 0} ")
                        }
                        withStyle(
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp
                            ).toSpanStyle()
                        ) {
                            append("Km/h")
                        }
                    }
                    // Visualizzazione della velocità e marcia attuale
                    Text(
                        text = annotatedString,
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        // Parte destra del cruscotto con informazioni sulla batteria
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 11.dp, end = 40.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(25.dp))
                .weight(1f),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Black,
            )
        ) {
            // Se è stato premuto il pulsante delle informazioni sulla batteria, mostra un popup con dettagli aggiuntivi
            if (batteryInfoClicked) {
                Popup(
                    alignment = Alignment.TopCenter,
                    onDismissRequest = { batteryInfoClicked = false }
                ) {
                    // Popup con informazioni sulla batteria
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .wrapContentHeight()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(25.dp)),
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Charging status: ${currentTelemetriesData?.battery_charging_status}")
                            Text(text = "Voltage: ${currentTelemetriesData?.battery_voltage ?: 0}V")
                            Text(text = "Current: ${currentTelemetriesData?.battery_current ?: 0}A")
                            Text(text = "State of health: ${currentTelemetriesData?.stateOfHealth ?: 0}%")
                            Text(text = "Remaining energy: ${currentTelemetriesData?.battery_remaining_energy ?: 0}Wh")
                            Text(text = "Remaining capacity: ${currentTelemetriesData?.battery_remaining_capacity ?: 0}Ah")
                            Text(text = "Average cells temperature: ${currentTelemetriesData?.average_cells_temperature ?: 0}°C")
                        }
                    }
                }
            }
            // Icona per attivare il popup delle informazioni sulla batteria
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Battery Info",
                tint = Color.White,
                modifier = Modifier
                    .size(80.dp)
                    .padding(20.dp)
                    .align(Alignment.End)
                    .clickable { batteryInfoClicked = true }
            )
            // Riga inferiore con l'indicatore di stato di carica della batteria
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Prima colonna
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 77.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${currentTelemetriesData?.stateOfCharge ?: 0}%",
                        color = Color.White,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "${currentTelemetriesData?.average_cells_temperature ?: 0}°C",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(bottom = 8.dp) // Aggiungi spazio tra i testi e Battery
                    )
                }

                // Seconda colonna
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(bottom = 155.dp, end = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Battery(
                        value = currentTelemetriesData?.stateOfCharge ?: 0,
                        modifier = Modifier
                            .height(170.dp)
                            .width(70.dp)
                            .aspectRatio(1.6f),
                        steps = 8,
                        batteryHeightMultiplier = 3.5f,
                        batteryWidthMultiplier = 1f
                    )
                }
            }

        }
    }
}