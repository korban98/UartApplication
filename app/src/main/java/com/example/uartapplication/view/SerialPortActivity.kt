package com.example.uartapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.uartapplication.controller.Controller
import com.example.uartapplication.model.entities.ChargingStates
import com.example.uartapplication.model.entities.TelemetriesData
import com.example.uartapplication.view.composable.Dashboard
import com.example.uartapplication.view.composable.ERROR
import com.example.uartapplication.view.composable.ErrorsManager
import com.example.uartapplication.view.composable.FAULT
import com.example.uartapplication.view.composable.FaultsManager
import com.example.uartapplication.view.composable.TopBar
import com.example.uartapplication.view.composable.GearsNavBar
import com.example.uartapplication.view.composable.WARNING
import com.example.uartapplication.view.composable.WarningsManager
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Activity che gestisce la connessione UART e la visualizzazione dei dati telemetrici e degli errori.
 *
 * @constructor Crea un'istanza di [SerialPort].
 */
@DelicateCoroutinesApi
@ExperimentalComposeUiApi
@SuppressLint("MutableCollectionMutableState")
@ExperimentalMaterial3Api
class SerialPort : AppCompatActivity() {

    private val controller: Controller = Controller.getInstance()

    // Dati telemetrici e variabili di stato
    private var telemetriesData by mutableStateOf<TelemetriesData?>(null)
    private var errors by mutableStateOf(mutableListOf<String>())
    private var faults by mutableStateOf(mutableListOf<String>())
    private var warnings by mutableStateOf(mutableListOf<String>())

    private var errorButtonClicked by mutableStateOf(false)
    private var warningButtonClicked by mutableStateOf(false)
    private var faultButtonClicked by mutableStateOf(false)

    /**
     * Chiamato quando l'attività è stata creata.
     *
     * @param savedInstanceState Stato dell'istanza salvato in precedenza, se disponibile.
     */
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurazione finestra
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Creazione dell'interfaccia utente composable
        setContent {

            // Controllo UI System
            val systemUiController: SystemUiController = rememberSystemUiController()

            SideEffect {
                // Nasconde la barra di stato e la barra di navigazione
                systemUiController.isStatusBarVisible = false
                systemUiController.isNavigationBarVisible = false
            }

            var colorErrorButton by remember { mutableStateOf(Color.LightGray) }

            // Effetto di lancio per l'aggiornamento del colore del pulsante degli errori
            LaunchedEffect(errors) {
                if (errors.isNotEmpty()) {
                    colorErrorButton = Color.White
                } else {
                    colorErrorButton = Color.LightGray
                }
            }

            // Layout principale
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x4D000000)),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Colonna contenente la topBar, la dashboard, la gestione degli errori e la gearBar
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    // Vista della topBar
                    TopBar(colorErrorButton, telemetriesData?.battery_charging_status ?: ChargingStates.NOT_SIGNIFICANT) { buttonId ->
                        when(buttonId) {
                            ERROR -> errorButtonClicked = true
                            WARNING -> warningButtonClicked = true
                            FAULT -> faultButtonClicked = true
                        }
                    }

                    // Vista della dashboard
                    Dashboard(telemetriesData)

                    // Vista della gestione degli errori
                    ErrorsManager(errors = errors, isClicked = errorButtonClicked) {
                        errorButtonClicked = false
                    }

                    // Vista della gestione degli errori
                    FaultsManager(faults = faults, isClicked = faultButtonClicked) {
                        faultButtonClicked = false
                    }

                    // Vista della gestione degli errori
                    WarningsManager(warning = warnings, isClicked = warningButtonClicked) {
                        warningButtonClicked = false
                    }
                }
                // Vista della gearBar
                GearsNavBar(telemetriesData?.currentGear)
            }
        }

        // Avvio della connessione UART e definizione della lambda per gestire i messaggi ricevuti
        val conn = controller.startUartConnection { newMessage ->
            var json =  controller.messageToJson(newMessage)
            json = json?.getJSONObject("values")
            if (json != null) {
                // Imposta i dati telemetrici
                val tmp = telemetriesData
                telemetriesData = null
                telemetriesData = controller.jsonToTelemetriesObj(json, tmp)

                if(telemetriesData!!.error != null) errors = checkDetailsErrorsReceived(telemetriesData!!.error, errors)
                if(telemetriesData!!.fault != null) faults = checkDetailsErrorsReceived(telemetriesData!!.fault, faults)
                if(telemetriesData!!.warning != null) warnings = checkDetailsErrorsReceived(telemetriesData!!.warning, warnings)
            }
        }

        // Gestione del risultato della connessione
        if (conn) {
            showToast("Success")
        } else {
            showToast("Fail")
        //    finishAffinity()        // Terminazione dell'app
        }
    }

    /**
     * Mostra un Toast con il messaggio specificato.
     *
     * @param msg Messaggio da visualizzare nel Toast.
     */
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * Verifica e aggiorna l'elenco degli errori in base al codice errore ricevuto
     * e alla specifica lista di errori.
     *
     * @param codeError Il codice errore ricevuto.
     * @param oldErrors L'elenco degli errori prima dell'aggiornamento.
     * @return L'elenco aggiornato degli errori dopo l'elaborazione del codice errore.
     */
    private fun checkDetailsErrorsReceived(codeError: Int?, oldErrors: MutableList<String>): MutableList<String> {

        var updatedErrors = oldErrors

        // Decodifica dell'errore per la visualizzazione sull'UI
        if(codeError != null) {
            val binaryString = controller.IntegerToBinaryString(codeError)!!.reversed()

            // viene controllato ogni carattere della stringa di byte
            binaryString.forEachIndexed { index, char ->
                val decodedError = controller.decodingErrorSignal(index)
                if (decodedError != null) {
                    if (char == '1') {
                        updatedErrors = updatedErrors.toMutableList()
                            .apply {
                                // Aggiungi l'errore attivo se non è già nella lista (quindi se era disattivo ed ora attivo)
                                if (decodedError.description !in updatedErrors) {
                                    add(decodedError.description)
                                }
                            }
                    } else {
                        updatedErrors = updatedErrors.toMutableList()
                            .apply {
                                // rimuove l'errore disattivo se è presente nella lista (quindi se era attivo ed ora disattivo)
                                if (decodedError.description in updatedErrors) {
                                    remove(decodedError.description)
                                }
                            }
                    }
                }
            }
        }
        return updatedErrors
    }

}