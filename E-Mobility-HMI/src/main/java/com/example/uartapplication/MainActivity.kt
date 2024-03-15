package com.example.uartapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.uartapplication.view.SerialPort
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Activity principale dell'applicazione.
 *
 * @constructor Crea un'istanza di [MainActivity].
 */
@DelicateCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    /**
     * Avvia l'attività [SerialPort].
     *
     * @param savedInstanceState Stato dell'istanza salvato in precedenza, se disponibile.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avvia l'attività SerialPort
        val intent = Intent(this, SerialPort::class.java)
        startActivity(intent)
    }
}