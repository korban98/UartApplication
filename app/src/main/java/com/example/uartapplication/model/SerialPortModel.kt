package com.example.uartapplication.model

import android.os.Handler
import com.example.uartapplication.controller.WpiControl
import com.example.uartapplication.model.utilities.RootCmd

/**
 * Questa classe gestisce la comunicazione seriale attraverso il protocollo UART implementato
 * dalla libreria nativa C importata nel progetto.
 * Utilizza la libreria WpiControl per le operazioni di apertura, chiusura, e invio/ricezione dati.
 *
 * @property handler Oggetto Handler per la gestione dei messaggi periodici.
 * @property fd File Descriptor per la comunicazione seriale.
 * @property delayMillis Intervallo di tempo (in millisecondi) tra le letture dei dati seriali.
 */
@Suppress("DEPRECATION")
class SerialPortModel {

    private var handler: Handler? = null
    var fd = 0
    var delayMillis: Long = 10

    companion object{
        const val BAUD: Int = 115200
        const val SERIAL_PORT: String = "/dev/ttyS0"        // change the value with the serial port used

        const val START_CHAR: Char = '\u003c'               // unicode for '<'
        const val TERMINATOR_CHAR: Char = '\u003e'          // unicode for '>'

        private var instance: SerialPortModel? = null

        /**
         * Ottiene un'istanza (Singleton) della classe SerialPortModel.
         *
         * @return Un'istanza della classe SerialPortModel.
         */
        @JvmStatic
        fun getInstance(): SerialPortModel {
            if(instance == null)
                instance = SerialPortModel()
            return instance as SerialPortModel
        }
    }

    /**
     * Avvia la connessione UART e inizia a ricevere dati se ve ne sono sulla seriale.
     * L'handler permette che periodicamente venga eseguita la lettura sulla seriale.
     * Vengono memorizzati nella stringa jsonString tutti i caratteri compresi tra <>.
     * jsonString può contenere al massimo 1024 caratteri.
     * jsonString esclusa di <> viene ritornata attraverso la callback.
     *
     * @param uartMessagesCallback Callback per gestire i messaggi UART ricevuti.
     * @return true se la connessione è stata avviata con successo, false altrimenti.
     */
    fun startConnection(uartMessagesCallback: (String) -> Unit): Boolean {
        RootCmd.execRootCmdSilent("chmod 666 $SERIAL_PORT")
        fd = WpiControl.serialOpen(SERIAL_PORT, BAUD)
        if (fd > 0) {
            handler = Handler()
            handler!!.postDelayed(object : Runnable {
                override fun run() {
                    var jsonString = ""
                    if(WpiControl.serialDataAvail(fd) > 0) {
                        var jsonStarted = false
                        var found = false
                        var byteCounter = 0
                        while (!found && byteCounter < 1024) {
                            val outputSize: Int = WpiControl.serialDataAvail(fd)
                            if (outputSize > 0) {
                                var charBuffer = ""
                                repeat(outputSize) {
                                    val char = WpiControl.serialGetchar(fd)
                                    if(char == TERMINATOR_CHAR.code && jsonStarted) {
                                        jsonStarted = false
                                        found = true
                                        return@repeat
                                    } else if(char == START_CHAR.code) {
                                        jsonStarted = true
                                        charBuffer = ""
                                        jsonString = ""
                                    }
                                    if(jsonStarted) {
                                        charBuffer += char.toChar()
                                        byteCounter++
                                    }
                                }
                                jsonString += charBuffer
                            }
                        }
                        uartMessagesCallback(jsonString.substring(1))   // rimuovo il primo carattere '<'
                    }
                    handler!!.postDelayed(this, delayMillis)
                }
            }, delayMillis)      //10
            return true
        } else { return false }
    }

    /**
     * Interrompe la connessione UART e chiude la comunicazione seriale.
     */
    fun stopConnection() {
        if (fd >= 0) {
            //WpiControl.serialFlush(fd)
            WpiControl.serialClose(fd)
        }
        fd = -1
    }

    /**
     * Invia un messaggio sulla connessione UART.
     *
     * @param msg Messaggio da inviare.
     */
    fun sendMessage(msg: String) {
        if (fd >= 0) {
            WpiControl.serialPuts(fd, msg)
        }
    }

}