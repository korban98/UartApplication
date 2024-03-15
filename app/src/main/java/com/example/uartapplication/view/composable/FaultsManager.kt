package com.example.uartapplication.view.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.uartapplication.view.composable.faultsList.GetFaultListDialog

/**
 * Composable che gestisce la visualizzazione dei faults e la gestione del loro stato.
 *
 * @param faults Lista dei faults attualmente attivi.
 * @param isClicked Flag che indica se il pulsante dei faults è stato premuto.
 * @param onErrorListClosed Callback chiamato quando la lista dei faults è stata chiusa.
 */
@Composable
fun FaultsManager(faults: List<String>, isClicked: Boolean, onErrorListClosed: () -> Unit) {

    // Flag che indica se il pulsante degli errori è stato premuto
    var faultButtonClicked by remember { mutableStateOf(false) }

    // Effetto di lancio basato sul flag isClicked
    LaunchedEffect(isClicked) {
        faultButtonClicked = isClicked
    }

    // Se il pulsante degli errori è stato premuto, mostra la dialog degli errori
    if (faultButtonClicked) {
        GetFaultListDialog(faults) {
            faultButtonClicked = false
            onErrorListClosed()
        }
    }
}