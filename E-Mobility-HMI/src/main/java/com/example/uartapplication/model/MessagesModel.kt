package com.example.uartapplication.model

import com.example.uartapplication.model.entities.TelemetriesData
import com.example.uartapplication.model.entities.ChargingStates
import com.example.uartapplication.model.entities.ErrorData
import com.example.uartapplication.model.entities.KeyStates
import org.json.JSONException
import org.json.JSONObject

/**
 * Questa classe gestisce la decodifica dei messaggi e fornisce metodi per la verifica della validità
 * degli errori, la decodifica dei dati telemetrici e la conversione di messaggi in oggetti JSON.
 *
 * @property telemetries Oggetto contenente i dati telemetrici.
 */
class MessagesModel {

    private val telemetries: TelemetriesData = TelemetriesData.getInstance()

    companion object {
        private var instance: MessagesModel? = null

        /**
         * Ottiene un'istanza (Singleton) della classe MessagesModel.
         *
         * @return Un'istanza della classe MessagesModel.
         */
        @JvmStatic
        fun getInstance(): MessagesModel {
            if (instance == null)
                instance = MessagesModel()
            return instance as MessagesModel
        }
    }

    /**
     * Decodifica una stringa di errore in un oggetto [ErrorData].
     *
     * @param errorIndex intero rappresentante il segnale di errore.
     * @return Un oggetto [ErrorData] corrispondente alla stringa di errore decodificata, o null se la decodifica fallisce.
     */
    fun decodingErrorBytes(errorIndex: Int): ErrorData? {
        return when(errorIndex) {
            0 -> ErrorData.OVER_VOLTAGE
            1 -> ErrorData.UNDER_TEMPERATURE//
            2 -> ErrorData.OVER_TEMPERATURE//
            3 -> ErrorData.OVER_RESISTANCE
            4 -> ErrorData.UNDER_RESISTANCE//
            5 -> ErrorData.OVER_TEMPERATURE_POWER_ZONE
            6 -> ErrorData.CURRENT_SENSOR//
            7 -> ErrorData.SPI_COMMUNICATION
            8 -> ErrorData.HIGH_VOLTAGE_INCONSISTENCY//
            9 -> ErrorData.RESERVED
            10 -> ErrorData.POWER_DEVICES//
            11 -> ErrorData.OVER_CURRENT
            12 -> ErrorData.CAN_COMMUNICATION//
            13 -> ErrorData.OVER_TEMPERATURE_LOGIC_ZONE
            14 -> ErrorData.UNDER_VOLTAGE//
            15 -> ErrorData.DISCHARGE_OVER_CURRENT
            else -> null
        }
    }

    /**
     * Decodifica il valore relativo lo stato di carica in un oggetto [ChargingStates].
     *
     * @param charge intero rappresentante lo stato di carica.
     * @return Un oggetto [ChargingStates] corrispondente alla stringa dello stato di carica decodificata,
     * o null se la decodifica fallisce.
     */
    private fun decodingChargingStatus(charge: Int): ChargingStates? {
        return when(charge) {
            0 -> ChargingStates.NOT_SIGNIFICANT
            1 -> ChargingStates.CHARGING
            2 -> ChargingStates.BALANCING
            4 -> ChargingStates.END_OF_CHARGE
            else -> null
        }
    }

    /**
     * Decodifica il valore relativo allo stato della chiave in un oggetto [KeyStates].
     *
     * @param key intero relativo allo stato della chiave.
     * @return Un oggetto [KeyStates] corrispondente alla stringa dello stto della chiave decodificata,
     * o null se la decodifica fallisce.
     */
    private fun decodingKeyStatus(key: Int): KeyStates? {
        return when(key) {
            0 -> KeyStates.OFF
            1 -> KeyStates.ON
            else -> null
        }
    }

    /**
     * Recupera i dati telemetrici da un oggetto JSON,
     * dove fallisce assegna all'attributo il valore dell'oggetto precedente.
     *
     * @param json Oggetto JSON contenente i dati telemetrici.
     * @param tmp Oggetto [TelemetriesData] precedentemente ottenuto (old telemetries).
     * @return Un oggetto [TelemetriesData] contenente i dati telemetrici decodificati.
     */
    fun decodingTelemetriesData(json: JSONObject, tmp: TelemetriesData?): TelemetriesData {
        telemetries.battery_voltage = try{json.getDouble("battery_voltage").toFloat()} catch (_:JSONException){ tmp?.battery_voltage }
        telemetries.battery_current = try{json.getDouble("battery_current").toFloat()} catch (_:JSONException){tmp?.battery_current}
        telemetries.stateOfHealth = try{json.getInt("battery_SOH_Perc")} catch (_:JSONException){ tmp?.stateOfHealth }
          telemetries.stateOfCharge = try{json.getInt("battery_SOC_Perc")} catch (_:JSONException){tmp?.stateOfCharge}
        telemetries.battery_remaining_energy = try{json.getDouble("battery_remaining_energy").toFloat()} catch (_:JSONException){tmp?.battery_remaining_energy}
        telemetries.battery_remaining_capacity = try{json.getDouble("battery_remaining_capacity").toFloat()} catch (_:JSONException){tmp?.battery_remaining_capacity}
        telemetries.battery_charging_status = try{decodingChargingStatus(json.getInt("battery_charging_status"))} catch (_:JSONException){tmp?.battery_charging_status}
        telemetries.average_cells_temperature = try{json.getInt("avg_cells_temp")} catch (_:JSONException){tmp?.average_cells_temperature}
        telemetries.keyStatus = try{decodingKeyStatus(json.getInt("KS"))} catch (_:JSONException){tmp?.keyStatus}                      // TODO: remove
        telemetries.velocityValue = try{json.getInt("velocity_value")} catch (_:JSONException){tmp?.velocityValue}
        telemetries.motorTemperatureL = try{json.getInt("MTL")} catch (_:JSONException){tmp?.motorTemperatureL}
        telemetries.motorTemperatureR = try{json.getInt("MTR")} catch (_:JSONException){tmp?.motorTemperatureR}
        telemetries.currentGear = try{json.getString("CG")} catch (_:JSONException){tmp?.currentGear}
        telemetries.error = try {json.getInt("battery_errors")} catch (_:JSONException){null}
        telemetries.fault = try {json.getInt("battery_faults")} catch (_:JSONException){null}
        telemetries.warning = try {json.getInt("battery_warnings")} catch (_:JSONException){null}

        return telemetries
    }

    /**
     * Converte una stringa di messaggio in un oggetto JSON.
     *
     * @param newMessage Stringa rappresentante il messaggio da convertire in JSON.
     * @return Un oggetto JSON se la conversione è riuscita, altrimenti null.
     */
    fun msgToJson(newMessage: String): JSONObject? {
        val json: JSONObject?
        try {
            json = JSONObject(newMessage)
        } catch (_: JSONException) {return null}
        return json
    }

}