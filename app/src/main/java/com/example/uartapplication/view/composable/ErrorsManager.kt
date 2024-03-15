package com.example.uartapplication.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.uartapplication.R
import com.example.uartapplication.view.composable.errorsList.GetErrorListDialog
import kotlinx.coroutines.delay

/**
 * Composable che gestisce la visualizzazione degli errori e la gestione del loro stato.
 *
 * @param errors Lista degli errori attualmente attivi.
 * @param isClicked Flag che indica se il pulsante degli errori è stato premuto.
 * @param onErrorListClosed Callback chiamato quando la lista degli errori è stata chiusa.
 */
@Composable
fun ErrorsManager(errors: List<String>, isClicked: Boolean, onErrorListClosed: () -> Unit) {
    // Lista degli errori visualizzati
    var errorList by remember { mutableStateOf(listOf<String>()) }

    // Flag che indica se il pulsante degli errori è stato premuto
    var errorButtonClicked by remember { mutableStateOf(false) }

    var oldErrorList by remember { mutableStateOf(listOf<String>()) }

    // Effetto di lancio basato sul flag isClicked
    LaunchedEffect(isClicked) {
        errorButtonClicked = isClicked
    }

    // Effetto di lancio basato sulla lista degli errori
    LaunchedEffect(errors) {
        // Incrementa il conteggio ogni volta che la lista degli errori cambia
        if (errors.isNotEmpty()) {
            errors.forEach { newError ->
                // Aggiungi l'errore se non è già nella lista
                if (newError !in oldErrorList) {
                    errorList = errorList + newError
                }
            }
        } else {
            oldErrorList = emptyList()
        }
    }

    // Se il pulsante degli errori è stato premuto, mostra la dialog degli errori
    if (errorButtonClicked) {
        LaunchedEffect(errors) {
            if (errors.isNotEmpty()) {
                oldErrorList = errorList
                errorList = emptyList()
            }
        }
        GetErrorListDialog(errors) {
            errorButtonClicked = false
            onErrorListClosed()
        }
    } else if (errorList.isNotEmpty()) {

        var currentError by remember { mutableStateOf("") }

        // Effetto di lancio basato sull'errore corrente
        LaunchedEffect(currentError) {
            // Attendere per 3 secondi (puoi cambiare il valore a seconda delle tue esigenze)
            delay(5000)

            // Rimuovere automaticamente l'errore dopo il periodo di attesa
            oldErrorList = errorList
            errorList = emptyList()
        }

        Dialog(
            onDismissRequest = {
                // Rimuovi l'errore dalla lista degli errori
                oldErrorList = errorList
                errorList = emptyList()
            }
        ) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xB3131313)
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val errorPainter = painterResource(id = R.drawable.error)
                        Icon(errorPainter, "Floating action Errors button.", tint = Color.Red)
                        Text(
                            text = "Errors",
                            modifier = Modifier.padding(10.dp),
                            color = Color.White
                        )
                    }
                    errorList.forEach { error ->
                        Text("Error: $error", color = Color.White)
                        currentError = error
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = {
                                oldErrorList = errorList
                                errorList = emptyList()
                            },
                            modifier = Modifier.padding(5.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}
