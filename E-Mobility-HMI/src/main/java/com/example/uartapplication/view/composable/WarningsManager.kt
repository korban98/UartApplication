package com.example.uartapplication.view.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.uartapplication.view.composable.warningsList.getWarningListDialog

/**
 * Composable che gestisce la visualizzazione dei warning e la gestione del loro stato.
 *
 * @param warning Lista dei warning attualmente attivi.
 * @param isClicked Flag che indica se il pulsante dei warning è stato premuto.
 * @param onErrorListClosed Callback chiamato quando la lista dei warning è stata chiusa.
 */
@Composable
fun WarningsManager(warning: List<String>, isClicked: Boolean, onErrorListClosed: () -> Unit) {

    // Flag che indica se il pulsante degli errori è stato premuto
    var warningButtonClicked by remember { mutableStateOf(false) }

    // Effetto di lancio basato sul flag isClicked
    LaunchedEffect(isClicked) {
        warningButtonClicked = isClicked
    }

    // Se il pulsante degli errori è stato premuto, mostra la dialog degli errori
    if (warningButtonClicked) {
        getWarningListDialog(warning) {
            warningButtonClicked = false
            onErrorListClosed()
        }
    }
}