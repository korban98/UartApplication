package com.example.uartapplication.controller

import com.example.uartapplication.model.entities.ErrorData
import com.example.uartapplication.model.MessagesModel
import com.example.uartapplication.model.SerialPortModel
import com.example.uartapplication.model.entities.TelemetriesData
import org.json.JSONObject

/**
 * Questa classe gestisce la comunicazione tra la vista (View) e il modello (Model).
 *
 * @constructor Crea un'istanza della classe Controller attraverso il Singleton pattern.
 */
class Controller {

    private val messagesModel: MessagesModel = MessagesModel.getInstance()
    private val serialPortModel: SerialPortModel = SerialPortModel.getInstance()

    companion object {
        private var instance: Controller? = null

        /**
         * Ottiene un'istanza (Singleton) della classe Controller.
         *
         * @return Un'istanza della classe Controller.
         */
        @JvmStatic
        fun getInstance(): Controller {
            if (instance == null)
                instance = Controller()
            return instance as Controller
        }
    }

    /**
     * Converte un oggetto JSON in un oggetto [TelemetriesData].
     *
     * @param json Oggetto JSON contenente i dati delle telemetrie.
     * @param tmp vecchio Oggetto TelemetriesData.
     * @return Un oggetto TelemetriesData costruito utilizzando i valori dal JSON.
     */
    fun jsonToTelemetriesObj(json: JSONObject, tmp: TelemetriesData?): TelemetriesData {
        return messagesModel.decodingTelemetriesData(json, tmp)
    }

    /**
     * Ottiene l'oggetto [ErrorData] associato ad un certo segnale di errore.
     *
     * @param error Stringa rappresentante il segnale di errore.
     * @return Un oggetto ErrorData associato al segnale di errore dopo la decodifica.
     */
    fun decodingErrorSignal(error: Int): ErrorData? {
        return messagesModel.decodingErrorBytes(error)
    }

    /**
     * Ottiene l'oggetto String associato ad un certo segnale di errore se la stringa di errore
     * rispetta la sintassi prevista.
     *
     * @param error intero rappresentante il segnale di errore.
     * @return Un oggetto String associato al segnale di errore dopo la decodifica,
     *         o null se la stringa di errore non è valida.
     */
    fun IntegerToBinaryString(error: Int): String? {
        return if (error >= 0) {
            var a = Integer.toBinaryString(error)
            while(a.length < 16) {
                a = "0$a"
            }
            a
        } else {
            null
        }
    }

    /**
     * Converte una stringa di messaggio in un oggetto JSON se la sintassi è corretta.
     *
     * @param msg Stringa rappresentante il messaggio da convertire in JSON.
     * @return Un oggetto JSON se la stringa rispetta la sintassi JSON, altrimenti null.
     */
    fun messageToJson(msg: String): JSONObject? {
        return messagesModel.msgToJson(msg)
    }

    /**
     * Avvia la comunicazione UART.
     *
     * @param uartMessagesCallback Callback per gestire i messaggi UART in arrivo.
     * @return true se la connessione è avvenuta con successo, false altrimenti.
     */
    fun startUartConnection(uartMessagesCallback: (String) -> Unit): Boolean {
        return serialPortModel.startConnection { uartMessagesCallback(it) }
    }
}